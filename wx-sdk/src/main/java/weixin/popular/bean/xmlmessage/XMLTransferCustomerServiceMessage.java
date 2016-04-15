package weixin.popular.bean.xmlmessage;

/**
 * 
 * 消息转发到多客服
 * @author LiYi
 *
 */
public class XMLTransferCustomerServiceMessage extends XMLMessage{

	private String kfAccount;
	
	/**
	 * 消息转发到多客服
	 * @param toUserName
	 * @param fromUserName
	 * @param kfAccount 可以为空
	 */
	public XMLTransferCustomerServiceMessage(String toUserName, String fromUserName,String kfAccount) {
		super(toUserName, fromUserName, "transfer_customer_service");
		this.kfAccount = kfAccount;
	}


	@Override
	public String subXML() {
		if(kfAccount == null){
			return "";
		}else{
			return "<TransInfo><KfAccount><![CDATA["+kfAccount+"]]></KfAccount></TransInfo>";
		}
	}

	
}
