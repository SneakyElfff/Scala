import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.{JButton, JFrame, JPanel, SwingUtilities}
import java.awt.Dimension
import javax.swing.{JFrame, JLabel,JTextField, JPanel, SwingUtilities}
import java.sql.{Connection, DriverManager, ResultSet}
import java.awt.Color

object ButtonModule {
  val frame = new JFrame("My Application")
  frame.setSize(800, 600)
  frame.setLayout(null)
  
  def main(args: Array[String]): Unit = {
    SwingUtilities.invokeLater(() => {
    val label_name = new JLabel("Title")
    val label_price = new JLabel("Price")
    val label_quant = new JLabel("Quantity")
    label_name.setBounds(50,120,100,20)
    label_price.setBounds(200,120,70,20)
    label_quant.setBounds(330,120,70,20)
    
    val insert = new JButton("Insert")
    val select = new JButton("Select")
    val get_max = new JButton("Max profit")
    val find = new JButton("Find")
    insert.setBounds(20,80,120,20)
    select.setBounds(150,80,150,20)
    get_max.setBounds(310,80,150,20)
    find.setBounds(470,80,150,20)
    
    val textField = new JTextField(20)
    val textField2 = new JTextField(20)
    val textField3 = new JTextField(20)
    textField.setBounds(20,150,130,20)
    textField2.setBounds(160,150,130,20)
    textField3.setBounds(300,150,130,20)
    
    frame.add(label_name)
    frame.add(label_price)
    frame.add(label_quant)
    
    frame.add(insert)
    frame.add(select)
    frame.add(get_max)
    frame.add(find)
    
    frame.add(textField)
    frame.add(textField2)
    frame.add(textField3)
    
    insert.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val url = "jdbc:mysql://localhost:3306/mydb"
        val username = "root"
        val password = "nina"
    
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(url, username, password)
        try {
          val stmt = conn.createStatement()
    
          val prod_name = textField.getText().trim()
          val price = textField2.getText().trim()
          val quantity = textField3.getText().trim()
    
          val res_check = stmt.executeQuery(s"SELECT product FROM warehouse WHERE product = '$prod_name'")
    
          if (res_check.next()) {
            textField.setText("Value already exists")
          } else {
            val res = stmt.executeUpdate("INSERT INTO warehouse VALUES ('$prod_name', '$price', '$quantity')")
    
            if (res > 0) {
              textField2.setText("")
              textField3.setText("")
              textField.setText("Added record")
            } else {
              textField.setText("Failed to add record")
            }
          }
        } finally {
          conn.close()
        }
      }
    })
    
    select.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
      {
        val url = "jdbc:mysql://localhost:3306/mydb"
        val username = "root"
        val password = "nina"
        
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(url, username, password)
        try {
          val stmt = conn.createStatement()
          
          val prod_name= textField.getText().toString().trim()
          
          val rs = stmt.executeQuery("SELECT * FROM warehouse WHERE product = '"+prod_name+"'")
          
          while (rs.next()) {
            val name = rs.getString("product")
            val price = rs.getInt("price")
            val quantity = rs.getInt("quantity")
            
            textField2.setText("" + price)
            textField3.setText("" + quantity)
          }
        } finally {
            conn.close()
        }
      }
      }
    })
    
    get_max.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
      {
        val url = "jdbc:mysql://localhost:3306/mydb"
        val username = "root"
        val password = "nina"
        
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(url, username, password)
        try {
          val stmt = conn.createStatement()
          
          val rs = stmt.executeQuery("SELECT product FROM warehouse WHERE (price * quantity) = (SELECT MAX(price * quantity) FROM warehouse)")
          
          if (rs.next()) {
            val prod_name = rs.getString("product")
            textField2.setText("")
            textField3.setText("")
            textField.setText(prod_name)
          } else {
            textField.setText("No data found")
          }
        } finally {
            conn.close()
        }
      }
      }
    })
    
    find.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
      {
        val url = "jdbc:mysql://localhost:3306/mydb"
        val username = "root"
        val password = "nina"
        
        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(url, username, password)
        try {
          val stmt = conn.createStatement()
          
          val price_cond = textField2.getText().toString().trim()
          val quant_cond = textField3.getText().toString().trim()
          
          val query =
            if (price_cond.nonEmpty && quant_cond.nonEmpty) {
              s"SELECT product FROM warehouse WHERE price >= $price_cond AND quantity >= $quant_cond"
            } else if (price_cond.nonEmpty) {
              s"SELECT product FROM warehouse WHERE price >= $price_cond"
            } else if (quant_cond.nonEmpty) {
              s"SELECT product FROM warehouse WHERE quantity >= $quant_cond"
            } else {
              "SELECT product FROM warehouse"
            }
          
          val rs = stmt.executeQuery(query)
          
          if (rs.next()) {
            val prod_name = rs.getString("product")
            textField.setText(prod_name)
          } else {
            textField.setText("No data found")
          }
        } finally {
            conn.close()
        }
      }
      }
    })
    
    frame.setBackground(Color.BLUE)
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
    })
  }
}