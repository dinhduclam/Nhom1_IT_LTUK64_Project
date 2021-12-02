/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import controller.TiemVaccine.AddTiemVaccineController;
import controller.TiemVaccine.UpdateTiemVaccineController;
import model.TiemVaccineModel;
import view.TiemVaccineView;

/**
 * @author Acer
 *
 */
public class TiemVaccineController {
	private TiemVaccineView tiemVaccineView = new TiemVaccineView();
	private TiemVaccineModel tiemVaccineModel = new TiemVaccineModel(tiemVaccineView);
	
	public static final String colName[] = {"Họ tên", "Số hộ chiếu/CCCD", "Lần tiêm", "Loại Vaccine", "Ngày tiêm", "Đơn vị tiêm chủng"};
	//	HoTen	Id	LanTiem	LoaiVaccine	LoVaccine	NgayTiem	DonViTiemChung	
	public TiemVaccineController() {
		tiemVaccineView.initialize();
		tiemVaccineView.setActionAddButton(new addBtnAction());
		tiemVaccineView.setActionUpdateButton(new updateBtnAction());
		tiemVaccineView.setActionDeleteButton(new deleteBtnAction());
		tiemVaccineView.setKeyListenerFind(new findKeyListener());
		try {
			tiemVaccineView.setDataForTable(colName, tiemVaccineModel.getData(""));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			tiemVaccineView.showMessage("Lỗi kết nối database");
		}
	}
	
	class addBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new AddTiemVaccineController(tiemVaccineModel);
		}
	}
	
	class updateBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				new UpdateTiemVaccineController(tiemVaccineModel, tiemVaccineView.getSelectedInfo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				tiemVaccineView.showMessage(e.getMessage());
			}
		}
	}
	
	class deleteBtnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
//			TiemVaccineView.getSelectedRow();
			try {
				tiemVaccineModel.delete((String) tiemVaccineView.getSelectedInfo()[1]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				tiemVaccineView.showMessage("Loi databse");
			} catch (Exception e1) {
				// TODO: handle exception
				tiemVaccineView.showMessage(e1.getMessage());
			}
		}
	}
	
	class findKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			try {
				tiemVaccineView.setDataForTable(colName, tiemVaccineModel.getData(tiemVaccineView.getTextToFind()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tiemVaccineView.showMessage("Loi databse");
			} catch (Exception e1) {
				// TODO: handle exception
				tiemVaccineView.showMessage(e1.getMessage());
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String[] args) {
		new TiemVaccineController();
//		TiemVaccine.add();
	}
	
	
}
