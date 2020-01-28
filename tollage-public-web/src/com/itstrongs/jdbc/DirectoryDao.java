package com.itstrongs.jdbc;

import java.util.List;

import com.itstrongs.tollage.Directory;

public interface DirectoryDao {

	int addDirectory(int key, Directory directory);
	
	List<Directory> queryChildDirectory(String id);
	
	Directory queryDirectory(String id);
	
	List<Directory> queryLastDirectory(String parentId);
	
	int deleteDirectory(int key, String id);
}
