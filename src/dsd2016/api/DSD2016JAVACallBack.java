package dsd2016.api;

import java.util.ArrayList;

public interface DSD2016JAVACallBack {
	public void call(int outErrorCode, ArrayList<String> outB64BadPics, StringBuilder outMsg);
}
