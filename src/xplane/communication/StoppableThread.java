package xplane.communication;
/**
* StoppableThread.java
* 
* Abstract superclass for all threads that need to be manually stoppable.
* 
* This is the implementation found in XHSI (High Resolution HSI for X-Plane)
* 
* Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
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

public abstract class StoppableThread extends Thread {

    protected volatile boolean keep_running = false;

    public void signal_stop() {
        this.keep_running = false;
    }

	public boolean isKeep_running() {
		return keep_running;
	}

	public void setKeep_running(boolean keepRunning) {
		keep_running = keepRunning;
	}
}
