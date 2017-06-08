package com.newspace.common.svnversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @description Ant创建svn版本文件
 * @author huqili
 * @date 2016年11月15日
 */
public class VersionTask extends Task {
	private String versionFileDir; // 版本依据的文件路径，就是根据哪个文件来定义当前版本号
	private String refid; // 版本引用
	private String sqlFileSuffix; // 脚本文件后缀
	private String docsFileSuffix; // 部署文档文件后缀

	public VersionTask() {
		//这里的文件后缀可以定义在build.xml里，方便修改。
		this.sqlFileSuffix = ".sql";
		this.docsFileSuffix = ".xls";
	}

	public void execute() throws BuildException {
		// 拿到大版本号
		String mainVersion = getProject().getProperty("mainVersion");

		// 获取版本文件的路径
		File dir = new File(VersionTask.this.getProject().getProperty("docs.file.dir"));
		System.out.println("version file directory:" + dir.getPath());

		if (!dir.exists()) {
			throw new BuildException("version file directory not exists.");
		}

		// 用来过滤不符合规格的文件名，并返回合格的文件；
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				// ^匹配一行的开头；$匹配一行的结束；*通配符
				return name.matches("^" + VersionTask.this.getProject().getProperty( "project.version.name") + "_V" + mainVersion + ".*xls$");
			}
		};

		File[] files = dir.listFiles(filter);
		// 定义当前版本文件
		String version = ""; // 当前版本文件，例如：OSS1.08
		String nextVersion = ""; // 下个版本文件，例如：OSS1.09
		boolean flag = true;

		
		String oldDocsFileName = ""; //当前版本docs文档名称
		String newDocsFileName = ""; //要生成的docs文件名称
		// 判断是否有符合条件的文件
		if ((files != null) && (files.length > 0)) {
			flag = false;
			Arrays.sort(files);
			oldDocsFileName = version = files[(files.length - 1)].getName();

			String oldMainVersion = "";
			int subVersion = 0;
			// 判断拿到的版本名称版本号的类型，既判断是1.00类型的还是1.00.01类型
			if (version.split("\\.").length == 3) // 1.00类型
			{
				// 拿到当前最新版本的主版本号 比如 OSS_V2.01中的 2
				oldMainVersion = version.substring(version.indexOf("_V") + 2, version.indexOf("."));
				// 拿到当前最新版本的子版本号比如OSS_V2.01中的 01
				subVersion = Integer.parseInt(version.substring(version.indexOf(".") + 1, version.indexOf(".") + 3));
			} 
			else if (version.split("\\.").length == 4) // 1.00.01类型
			{
				// 拿到当前最新版本的主版本号 比如 OSS_V2.01.12中的 2.01
				oldMainVersion = version.substring(version.indexOf("_V") + 2,version.indexOf(".") + 3);
				// 拿到当前最新版本的子版本号比如OSS_V2.01.12中的 12
				subVersion = Integer.parseInt(version.substring(version.indexOf(".") + 4, version.indexOf(".") + +6));
			} 
			else 
			{
				flag = true;
			}

			//拼装版本名称
			version = "V" + mainVersion + ".";
			newDocsFileName = getProject().getProperty("project.version.name") + "_V" + mainVersion + ".";
			int newSubVersion = subVersion + 1;

			// 判断如果拿到的大版本号等于当前的版本
			if (oldMainVersion.equals(mainVersion)) 
			{
				// 下一个版本文件名
				nextVersion = version + getSubVersion(newSubVersion);
				// 当前版本名
				version = version + getSubVersion(subVersion);
				newDocsFileName = newDocsFileName + getSubVersion(newSubVersion);
			} 
			else
			{
				flag = true;
			}
		}

		// 如果不存在符合条件的文件，或者拿到的大版本号不等于当前版本的大版本号，则从当前拿到的大版本号开始，从00开始；
		// 比如当前版本2.01，拿到的大版本号为3，则本次用3.00版本
		if (flag)
		{
//			version = getProject().getProperty("project.short.name") + "_V" + mainVersion + ".";
			version ="V" + mainVersion + ".";
			nextVersion = version + "01";
			version = version + "00";
			//新的docs文档名称
			newDocsFileName = getProject().getProperty("project.version.name") + "_V" + mainVersion + ".01";
		}

		// 创建下个版本的脚本和部署文件
		File sqlFile = new File(getProject().getProperty("db.file.dir") + File.separator + nextVersion + this.sqlFileSuffix);
		String oldDocsFile = getProject().getProperty("docs.file.dir") + File.separator + oldDocsFileName;
		String newDocsFile = getProject().getProperty("docs.file.dir") + File.separator + newDocsFileName + this.docsFileSuffix;
		try 
		{
			if(getProject().getProperty("create_db_script").equals("true"))
			{
				//创建脚本文件
				sqlFile.createNewFile();
			}
			
			//当存在符合条件的docs文档里，复制一份，并命名为下一个版本
			if(!flag)  
			{
				copyFile(oldDocsFile, newDocsFile);
			}
			else
			{
				File docsFile = new File(newDocsFile);
				docsFile.createNewFile();
			}
			System.out.println("create modify file finished...");
		} 
		catch (IOException e) 
		{
			throw new BuildException("create or copy modify file fail..."+ e.getMessage());
		}

		getProject().setProperty(this.refid, version);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	@SuppressWarnings("resource")
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制docs文档时出错...");
		}

	}

	// 测试代码
	public static void main(String[] args) {

		// 拿到大版本号
		String mainVersion = "8";

		// 获取版本文件的路径
		File dir = new File("D://workspaces//Eclipse Luna//OSS//docs//modify");
		System.out.println("version file directory:" + dir.getPath());

		if (!dir.exists()) {
			throw new BuildException("version file directory not exists.");
		}

		// 用来过滤不符合规格的文件名，并返回合格的文件；
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				// ^匹配一行的开头；$匹配一行的结束；*通配符
				return name.matches("^OSS_部署文档_V" + mainVersion+ ".*xls$");
			}
		};

		File[] files = dir.listFiles(filter);
		// 定义当前版本文件
		String version = ""; // 当前版本文件，例如：OSS1.08
		String nextVersion = ""; // 下个版本文件，例如：OSS1.09
		boolean flag = true;

		
		String oldDocsFileName = ""; //当前版本docs文档名称
		String newDocsFileName = ""; //要生成的docs文件名称
		// 判断是否有符合条件的文件
		if ((files != null) && (files.length > 0)) {
			flag = false;
			Arrays.sort(files);
			oldDocsFileName = version = files[(files.length - 1)].getName();

			String oldMainVersion = "";
			int subVersion = 0;
			// 判断拿到的版本名称版本号的类型，既判断是1.00类型的还是1.00.01类型
			if (version.split("\\.").length == 3) // 1.00类型
			{
				// 拿到当前最新版本的主版本号 比如 OSS_V2.01中的 2
				oldMainVersion = version.substring(version.indexOf("_V") + 2,
						version.indexOf("."));
				// 拿到当前最新版本的子版本号比如OSS_V2.01中的 01
				subVersion = Integer.parseInt(version.substring(
						version.indexOf(".") + 1, version.indexOf(".") + 3));
			} 
			else if (version.split("\\.").length == 4) // 1.00.01类型
			{
				// 拿到当前最新版本的主版本号 比如 OSS_V2.01.12中的 2.01
				oldMainVersion = version.substring(version.indexOf("_V") + 2,
						version.indexOf(".") + 3);
				// 拿到当前最新版本的子版本号比如OSS_V2.01.12中的 12
				subVersion = Integer.parseInt(version.substring(
						version.indexOf(".") + 4, version.indexOf(".") + +6));
			} 
			else 
			{
				flag = true;
			}

			//拼装版本名称
			version = "OSS_V" + mainVersion + ".";
			newDocsFileName = "OSS_部署文档_V" + mainVersion + ".";
			int newSubVersion = subVersion + 1;

			// 判断如果拿到的大版本号等于当前的版本
			if (oldMainVersion.equals(mainVersion)) 
			{
				// 下一个版本文件名
				nextVersion = version + newSubVersion;
				// 当前版本名
				version = version + subVersion;
				newDocsFileName = newDocsFileName + newSubVersion;
			} 
			else
			{
				flag = true;
			}
		}

		// 如果不存在符合条件的文件，或者拿到的大版本号不等于当前版本的大版本号，则从当前拿到的大版本号开始，从00开始；
		// 比如当前版本2.01，拿到的大版本号为3，则本次用3.00版本
		if (flag)
		{
			version = "OSS_V" + mainVersion + ".";
			nextVersion = version + "01";
			version = version + "00";
			
			newDocsFileName = "OSS_部署文档_V" + mainVersion + ".01";
		}

		// 创建下个版本的脚本和部署文件
		File sqlFile = new File("D://workspaces//Eclipse Luna//OSS//db_script//modify" + File.separator + nextVersion + ".sql");
		String oldDocsFile = "D://workspaces//Eclipse Luna//OSS//docs//modify" + File.separator + oldDocsFileName;
		String newDocsFile = "D://workspaces//Eclipse Luna//OSS//docs//modify" + File.separator + newDocsFileName + ".xls";
		try 
		{
			//创建脚本文件
			sqlFile.createNewFile();
			
			//当存在符合条件的docs文档里，复制一份，并命名为下一个版本
			if(!flag)  
			{
				copyFile(oldDocsFile, newDocsFile);
			}
			else
			{
				File docsFile = new File(newDocsFile);
				docsFile.createNewFile();
			}
		} 
		catch (IOException e) 
		{
			throw new BuildException("create or copy modify file fail..."+ e.getMessage());
		}

		System.out.println("version:" + version);
	

	}

	// 保证小版本号为2位数
	private String getSubVersion(int subVersion) {
		String i = "00" + subVersion;
		return i.substring(i.length() - 2, i.length());
	}

	public String getVersionFileDir() {
		return this.versionFileDir;
	}

	public void setVersionFileDir(String versionFileDir) {
		this.versionFileDir = versionFileDir;
	}

	public String getRefid() {
		return this.refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String getSqlFileSuffix() {
		return sqlFileSuffix;
	}

	public void setSqlFileSuffix(String sqlFileSuffix) {
		this.sqlFileSuffix = sqlFileSuffix;
	}

	public String getDocsFileSuffix() {
		return docsFileSuffix;
	}

	public void setDocsFileSuffix(String docsFileSuffix) {
		this.docsFileSuffix = docsFileSuffix;
	}

}