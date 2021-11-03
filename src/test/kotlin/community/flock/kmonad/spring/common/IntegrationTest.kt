package community.flock.kmonad.spring.common

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import community.flock.kmonad.core.common.define.Data
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.sith.data.Sith
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun testJediHandler() {
        testCrud(
            "/jedi",
            Jedi(name = "Mace Windu", age = 54),
            Jedi(name = "Rey", age = 21)
        )
    }

    @Test
    fun testSithHandler() {
        testCrud(
            "/sith",
            Sith(name = "Darth Plagueis", age = 123),
            Sith(name = "Darth Sidious", age = 234)
        )
    }

    @Test
    fun testWieldersHandler() {
        val jedi = Jedi(name = "Mace Windu", age = 54)
        val sith = Sith(name = "Darth Plagueis", age = 123)

        mockMvc.post("/jedi") {
            contentType = MediaType.APPLICATION_JSON
            content = jedi.toJSON()
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }

        mockMvc.post("/sith") {
            contentType = MediaType.APPLICATION_JSON
            content = sith.toJSON()
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }

        mockMvc.get("/force-wielders").andExpect {
            status { isOk() }
            content {
                jsonPath("$[0]", containsString("LIGHT"))
                jsonPath("$[1]", containsString("LIGHT"))
                jsonPath("$[2]", containsString("DARK"))
                jsonPath("$[3]", containsString("DARK"))
            }
        }

        mockMvc.get("/force-wielders/${jedi.id}").andExpect {
            status { isOk() }
            content { json("{forceType: LIGHT}", strict = false) }
            content { json("{id: ${jedi.id}}", strict = false) }
        }
    }

    private fun testCrud(resource: String, item1: Data, item2: Data): ResultActionsDsl {
        mockMvc.post(resource) {
            contentType = MediaType.APPLICATION_JSON
            content = item1.toJSON()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(item1.toJSON()) }
        }

        mockMvc.post(resource) {
            contentType = MediaType.APPLICATION_JSON
            content = item2.toJSON()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(item2.toJSON()) }
        }

        mockMvc.post(resource) {
            contentType = MediaType.APPLICATION_JSON
            content = item2.toJSON()
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isConflict() } }

        mockMvc.get(resource).andExpect {
            status { isOk() }
        }

        mockMvc.get("$resource/${item1.id}").andExpect {
            status { isOk() }
            content { json(item1.toJSON()) }
        }

        mockMvc.get("$resource/${UUID.randomUUID()}").andExpect {
            status { isNotFound() }
        }

        mockMvc.delete("$resource/${item2.id}").andExpect {
            status { isOk() }
            content { json(item2.toJSON()) }
        }

        return mockMvc.delete("$resource/${item2.id}").andExpect {
            status { isNotFound() }
        }
    }

    private fun Any.toJSON() = jacksonObjectMapper().writeValueAsString(this)

}
