package main.service;


import main.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TagService {

    public boolean saveTag(Tag tag) throws SQLException;

    public boolean searchTag(String name) throws SQLException;

    public ArrayList<Tag> getAllDetails() throws SQLException;

    public boolean updateTag(Tag tag) throws SQLException;

    public boolean deleteTag(int key) throws SQLException;

}
