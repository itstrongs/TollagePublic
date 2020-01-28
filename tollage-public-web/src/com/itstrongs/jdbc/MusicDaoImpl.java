package com.itstrongs.jdbc;

import com.itstrongs.imusic.Music;
import com.itstrongs.tollage.DbcpUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * 渠道数据库接口实现
 * 
 * @author itstrong
 * 
 */
public class MusicDaoImpl implements MusicDao {

	private QueryRunner qr = new QueryRunner(DbcpUtils.getDataSource());

	public int addMusic(Music music) {
		int result = 0;
		try {
			String sql = "insert into music(name, parent_id) values(?, ?,)";
			result = qr.update(sql, music.getName(), music.getParent_id());
		} catch (SQLException e) {
			System.out.println("添加音乐失败.........");
			e.printStackTrace();
		}
		return result;
	}
}
