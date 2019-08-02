package com.accounting.bean;

public class BizResponse {

	private String result;
	private String tip;

	public BizResponse(String result, String tip) {
		this.tip = tip;
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return "BizResponse [result=" + result + ", tip=" + tip + "]";
	}

	public static class Builder {

		private String result;
		private String tip;

		public Builder success() {
			this.result = "1";
			return this;
		}

		public Builder fail() {
			this.result = "0";
			return this;
		}

		public Builder tip(String tip) {
			this.tip = tip;
			return this;
		}

		public BizResponse build() {
			return new BizResponse(result, tip);
		}
	}
}