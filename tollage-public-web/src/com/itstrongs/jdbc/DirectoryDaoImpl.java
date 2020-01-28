package com.itstrongs.jdbc;

import java.sql.SQLException;
import java.util.List;

import com.itstrongs.tollage.DbcpUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itstrongs.tollage.Directory;

/**
 * 渠道数据库接口实现
 * 
 * @author itstrong
 * 
 */
public class DirectoryDaoImpl implements DirectoryDao {

	private QueryRunner qr = new QueryRunner(DbcpUtils.getDataSource());
 
	public int addDirectory(int key, Directory directory) {
		int resule = 0;
		try {
			String sql = "update directory d set d.key = d.key + 1 where d.key > ?";
			resule = qr.update(sql, key);
			sql = "insert into directory(id, directory.key, name, parent_id, extension) values(?, ?, ?, ?, ?)";
			resule = qr.update(sql, directory.getId(), directory.getKey(), directory.getName(), directory.getParent_id(), directory.getExtension());
		} catch (SQLException e) {
			System.out.println("添加目录失败.........");
			e.printStackTrace();
		}
		return resule;
	}
	
	public List<Directory> queryChildDirectory(String parentId) {
		List<Directory> directories = null;
		try {
			String sql = "select * from directory where parent_id = ? order by 2";
			directories = qr.query(sql, new BeanListHandler<Directory>(Directory.class), parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询渠道失败...........");
		}
		return directories;
	}

	public int deleteDirectory(int key, String id) {
		int resule = 0;
		try {
			String sql = "delete from directory where id = ?";
			resule = qr.update(sql, id);
			sql = "update directory d set d.key = d.key - 1 where d.key > ?";
			resule = qr.update(sql, key);
		} catch (SQLException e) {
			System.out.println("删除指定渠道失败.........");
		}
		return resule;
	}

	public Directory queryDirectory(String id) {
		Directory directory = null;
		try {
			String sql = "select * from directory where id=?";
			System.out.println("sql:" + sql + id);
			directory = qr.query(sql, new BeanHandler<Directory>(Directory.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询渠道失败...........");
		}
		return directory;
	}

	public List<Directory> queryLastDirectory(String parentId) {
		List<Directory> directories = null;
		try {
			String sql = "select * from directory where parent_id = (select parent_id from directory where id = ?) order by 2";
			directories = qr.query(sql, new BeanListHandler<Directory>(Directory.class), parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询渠道失败...........");
		}
		return directories;
	}
	
}
