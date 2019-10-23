import java.nio.file.Files
import java.nio.file.Paths
import java.sql.DriverManager
import java.util.*

fun main() {
    val props = Properties()
    props.load(Files.newBufferedReader(Paths.get("props.ini")))
    val connection = DriverManager.getConnection(props["url"].toString(), props)
    val db = DBMain(connection)
    do {
        println("Choose one of:")
        println("1 - show all")
        println("2 - add person")
        println("3 - delete person")
        println("4 - update person")
        println("0 - exit")
        val s = readLine()!!.toInt()
        when (s) {
            1 -> db.showAll()
            2 -> {
                println("New Person")
                print("name: ")
                val name = readLine()
                print("age: ")
                val age = readLine()!!.toInt()
                db.add(name, age)
            }
            3 -> {
                println("Delete person. Enter id:")
                val id = readLine()!!.toInt()
                db.delete(id)
            }
            4 -> {
                println("Update person. Enter id:")
                val id = readLine()!!.toInt()
                val person = db.find(id)
                if (person.id != 0) {
                    println(person)
                    print("new age: ")
                    val age = readLine()!!.toInt()
                    db.updateAge(person, age)
                }
            }
        }
    } while (s>0)
}