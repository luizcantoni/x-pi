package xplane.message;

import java.nio.ByteBuffer;

/**
* XPlaneUDPMessage.java
* 
* Base class for all types of messages sent and received from the X-Plane.
* 
* Copyright (C) 2010 Luiz Cantoni (luiz.cantoni@gmail.com)
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
public abstract class XPlaneUDPMessage {
	private String prolouge;

	public String getProlouge() {
		return prolouge;
	}

	public void setProlouge(String prolouge) {
		this.prolouge = prolouge;
	}

	public abstract ByteBuffer toByteBuffer();
	public abstract ByteBuffer toByteBufferWithoutPrologue();
}
