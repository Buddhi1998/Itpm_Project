package main.constants;

public class Constants {

    private Constants() {
    }

    public static final String QUERY_ACADEMIC_YEAR=" create table academicYearAndSemester(" +
                                                    "id int not null auto_increment," +
                                                    "yearName varchar(100)not null ," +
                                                    "semesterName varchar (100) not null," +
                                                    "fullName varchar(100) not null," +
                                                    "constraint primary key(id)" +
                                                    ");";

    public static final String PROFESSOR ="Professor";
}
