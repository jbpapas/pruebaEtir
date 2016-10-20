package es.dipucadiz.etir.comun.vo;

public class SqlLogVO {
	private String log = "";
	private String sql = "";
	private String cmd = "";
	
	public SqlLogVO(String log, String sql, String cmd) {
		this.log = log;
		this.sql = sql;
		this.cmd = cmd;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
}
