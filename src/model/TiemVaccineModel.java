package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import controller.CachLyController;
import view.CachLyView;

public class TiemVaccineModel {
	CachLyView cachLyView = null; 
	private final String databaseName = "ql_tiem_vaccine";
	private final String insertSQL = "INSERT INTO " + databaseName + " VALUE (?, ?, ?, ?, ?, ?, ?)"; 
	private final String updateSQL = "UPDATE " + databaseName + " SET LoaiVaccine = ?, LoVaccine = ?, NgayTiem = ?, DonViTiemChung = ? WHERE Id = ? AND LanTiem = ?";
	private final String selectAllSQL = "SELECT * FROM " + databaseName;
	private final String deleteSQL = "DELETE FROM " + databaseName + " WHERE Id = ? AND LanTiem = ?";
//	HoTen	Id	LanTiem	LoaiVaccine	LoVaccine	NgayTiem	DonViTiemChung	
	public TiemVaccineModel(CachLyView cachLyView) {
		this.cachLyView = cachLyView;
	}
	
	public void insert(Object[] data) throws SQLException{
		Connection con = SQLConnector.getCon();
		PreparedStatement stmt = con.prepareStatement(insertSQL);
//		HoTen	Id	NgayBatDau	MucDoCachLy	DiaChiCachLy
		stmt.setString(1, (String) data[0]);
		stmt.setString(2, (String) data[1]);
		stmt.setDate(3, (Date) data[2]);
		stmt.setInt(4, (int) data[3]);
		stmt.setString(5, (String) data[4]);
		
		String name = NhanKhauModel.getHoTen((String)data[1]);
		if (!name.equals(""))
			if (!data[0].equals(name)) throw new SQLException("Nhân khẩu có mã CCCD = " + data[1] + " tên là " + name);
		
		try {
			stmt.execute();
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			throw new SQLException("Không tồn tại nhân khẩu có CCCD là " + data[1] + " trong dữ liệu nhân khẩu");
		}
		con.close();
		cachLyView.setDataForTable(CachLyController.colName, getData(cachLyView.getTextToFind()));
	}
	
	public void update(Object[] data) throws SQLException{
		Connection con = SQLConnector.getCon();
		PreparedStatement stmt = con.prepareStatement(updateSQL);
//		HoTen	Id	NgayBatDau	MucDoCachLy	DiaChiCachLy
		stmt.setDate(1, (Date) data[2]);
		stmt.setInt(2, (int) data[3]);
		stmt.setString(3, (String) data[4]);
		stmt.setString(4, (String) data[1]);
		stmt.execute();
		con.close();
		cachLyView.setDataForTable(CachLyController.colName, getData(cachLyView.getTextToFind()));
	}
	
	public void delete(String id, Date ngayBatDau) throws SQLException {
		Connection con = SQLConnector.getCon();
		PreparedStatement stmt = con.prepareStatement(deleteSQL);
		stmt.setString(1, id);
		stmt.setDate(2, ngayBatDau);
		stmt.execute();
		con.close();
		cachLyView.setDataForTable(CachLyController.colName, getData(cachLyView.getTextToFind()));
	}
	
	public ArrayList<Object[]> getData(String condition) throws SQLException{
		String query = selectAllSQL;
		if (!condition.equals("")) query = selectAllSQL + " WHERE Id LIKE ? OR HoTen LIKE ?";
		
		Connection con = SQLConnector.getCon();
		PreparedStatement stmt = con.prepareStatement(query);
//		HoTen	Id	NgayBatDau	MucDoCachLy	DiaChiCachLy
		if (!condition.equals("")) {
			condition = "%" + condition + "%";
			stmt.setString(1, condition);
			stmt.setString(2, condition);
		}
		ResultSet rs = stmt.executeQuery();
		ArrayList<Object[]> data = new ArrayList<>();
		while (rs.next()) {
			Object[] row = new Object[6];
			row[0] = rs.getString("HoTen");
			row[1] = rs.getString("Id");
			row[2] = rs.getDate("NgayBatDau");
//			System.out.println(((Date) row[2]));
			row[3] = rs.getInt("MucDoCachLy");
			row[4] = rs.getString("DiaChiCachLy");
			data.add(row);
		}
		con.close();
		return data;
	}
	

}
