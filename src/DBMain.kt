import java.sql.Connection

class DBMain(connection: Connection) {
    val conn: Connection = connection

    fun showAll() {
        val statement = conn.createStatement()
        val people = mutableListOf<Person>()
        statement.executeQuery("select * from person").use { resultSet ->
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val age = resultSet.getInt("age")
                println("$id $name $age")
                people.add(Person(id, name, age))
            }
        }
        people.forEach { println(it) }
    }

    fun add(name: String?, age: Int) {
        val ps = conn.prepareStatement("insert INTO person (name, age) VALUES (?,?)")
        ps.setString(1, name)
        ps.setInt(2, age)
        ps.executeUpdate()
    }

    fun delete(id: Int) {
        val ps = conn.prepareStatement("delete from person where id = ?")
        ps.setInt(1, id)
        ps.executeUpdate()
    }

    fun find(id: Int) : Person {
        val ps = conn.prepareStatement("select * from person where id = ?")
        ps.setInt(1, id)
        val rs = ps.executeQuery()
        if (rs.next()) {
            return Person(rs.getInt("id"), rs.getString("name"), rs.getInt("age"))
        } else return Person(0,"",0)
    }

    fun updateAge(person: Person, age: Int) {
        val ps = conn.prepareStatement("update person set age = ? where id = ?")
        ps.setInt(1, age)
        ps.setInt(2, person.id)
        ps.executeUpdate()
    }
}








