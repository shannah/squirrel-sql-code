package net.sourceforge.squirrel_sql.fw.datasetviewer;
/*
 * Copyright (C) 2001 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
//import java.awt.BorderLayout;
import java.awt.Component;
//import java.awt.Font;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.SwingConstants;

import net.sourceforge.squirrel_sql.fw.gui.PropertyPanel;
import net.sourceforge.squirrel_sql.fw.util.IMessageHandler;

//??RENAME to DataSetViewerPropertyDestination
public class DataSetViewerPropertyPanel extends BaseDataSetViewerDestination 
{
	/** Component to be displayed. */
	private PropertyPanel _comp = new PropertyPanel();
	
	public DataSetViewerPropertyPanel() {
		super();
	}

	protected void addRow(Object[] row) {
		//_leftData.add(row[0]);
		//_rightData.add(row[1]);
		JLabel left = new JLabel(row[0].toString());
		JLabel right = new JLabel(row[1].toString());
		_comp.add(left, right);
	}

	protected void allRowsAdded() {
		/*
		for (int i = 0, limit = Math.max(_leftData.size(), _rightData.size());
				i < limit; ++i) {
			JLabel left = new JLabel(i < _leftData.size() ? (_leftData.get(i)).toString() : " ", SwingConstants.RIGHT);
			JLabel right = new JLabel( i < _rightData.size() ? (_rightData.get(i)).toString() : " ");
			add(left, right);
		}
		*/
	}
	
	protected Object formatValue(Object object)
	{
		if(object != null) return object.toString();
		return "<null>";
	}

	public Component getComponent() {
		return _comp;
	}
		
	public void moveToTop() {
	}

	public void clear() {
		_comp.removeAll();
	}


	/*
	 * @see IDataSetViewer#getRowCount()
	 */
	public int getRowCount() {
		return _comp.getComponentCount();
	}

}