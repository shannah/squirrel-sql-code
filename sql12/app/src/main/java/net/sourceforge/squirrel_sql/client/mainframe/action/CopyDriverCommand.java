package net.sourceforge.squirrel_sql.client.mainframe.action;
/*
 * Copyright (C) 2001-2003 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
import net.sourceforge.squirrel_sql.fw.sql.ISQLDriver;
import net.sourceforge.squirrel_sql.fw.util.ICommand;

import net.sourceforge.squirrel_sql.client.IApplication;

import javax.swing.*;

/**
 * This <CODE>ICommand</CODE> allows the user to copy an existing
 * <TT>ISQLDriver</TT> to a new one and then maintain the new one.
 *
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public class CopyDriverCommand implements ICommand
{
	/** Application API. */
	private final IApplication _app;

	/** <TT>ISQLDriver</TT> to be copied. */
	private final ISQLDriver _sqlDriver;

	/**
	 * Ctor.
	 *
	 * @param	app			Application API.
	 * @param	sqlDriver	<TT>ISQLDriver</TT> to be copied.
	 *
	 * @throws	IllegalArgumentException
	 *			Thrown if a <TT>null</TT> <TT>ISQLDriver</TT> passed.
	 */
	public CopyDriverCommand(IApplication app, ISQLDriver sqlDriver)
		throws IllegalArgumentException
	{
		super();
		if (app == null)
		{
			throw new IllegalArgumentException("IApplication == null");
		}
		if (sqlDriver == null)
		{
			throw new IllegalArgumentException("ISQLDriver == null");
		}

		_app = app;
		_sqlDriver = sqlDriver;
	}

	public void execute()
	{
		_app.getWindowManager().showCopyDriverInternalFrame(_sqlDriver);
	}
}
