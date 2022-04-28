package uz.pdp.service;

import uz.pdp.model.Result;
import uz.pdp.model.User;

import java.sql.*;

public class DbService {
    String url ="jdbc:postgresql://localhost:5432/app-auth";
    String dbUser = "postgres";
    String dbPassword = "Bexruz1211";

   public Result registerUser(User user){
       try {
           Class.forName("org.postgresql.Driver");
           //connection to db
           Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
           //send queries
           Statement statement = connection.createStatement();
           String checkPhoneNumberQuery = " select count(*) from users where phone_number = '" + user.getPhoneNumber()+"'";
           ResultSet resultSet = statement.executeQuery(checkPhoneNumberQuery);
           int countField = 0;
           while (resultSet.next()){
               countField = resultSet.getInt(1);
           }
           if (countField>0){
            return new Result("Phone number already exist",false);
           }

           String checkUsernameQuery = " select count(*) from users where username = '" + user.getUsername()+"'";
          ResultSet resultSetUsername = statement.executeQuery(checkUsernameQuery);
          while (resultSetUsername.next()){
              countField = resultSetUsername.getInt(1);
          }
           if (countField>0){
               return new Result("Username already exist",false);
           }
           PreparedStatement preparedStatement =
                   connection.prepareStatement
                           ( "insert into users(username, first_name, last_name, phone_number, password) values (?,?,?,?,?);  ");
           preparedStatement.setString(1, user.getUsername());
           preparedStatement.setString(2, user.getFirstName());
           preparedStatement.setString(3, user.getLastName());
           preparedStatement.setString(4, user.getPhoneNumber());
           preparedStatement.setString(5, user.getPassword());

          boolean execute = preparedStatement.execute();
           System.out.println(execute);

           return new Result("succesfully register",true);
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
           return new Result("error in server",false);
       }
   }

   public User login(String username, String password){
       try {

           Class.forName("org.postgresql.Driver");

           Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);


           PreparedStatement preparedStatement =
                   connection.prepareStatement("Select * from users where username = ? and password = ?");
             preparedStatement.setString(1,username);
             preparedStatement.setString(2,password);
             ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 int id = resultSet.getInt(1);
                 username = resultSet.getString(2);
                 String firstName = resultSet.getString(3);
                 String lastName = resultSet.getString(4);
                 String phoneNumber = resultSet.getString(5);
                User  user = new User(id,firstName,lastName,phoneNumber,username);
                 return user;
             }
             return null;
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }

       return null;
   }

    public User loadUserByCookie(String username){
        try {

            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);


            PreparedStatement preparedStatement =
                    connection.prepareStatement("Select * from users where username = ? ");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                User  user = new User(id,firstName,lastName,phoneNumber,username);
                return user;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
