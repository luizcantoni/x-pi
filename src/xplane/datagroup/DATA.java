package xplane.datagroup;

public class DATA {
	private int message;
	private int parameter;
	private String unit;
	private String convertTo;
	private String desc;
	private String name;
	private String XPlaneName;
	private DATAGroup group;
	private float value;
	private boolean readOnly;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DATAGroup getGroup() {
		return group;
	}
	public void setGroup(DATAGroup group) {
		this.group = group;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getConvertTo() {
		return convertTo;
	}
	public void setConvertTo(String convertTo) {
		this.convertTo = convertTo;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public int getParameter() {
		return parameter;
	}
	public void setParameter(int parameter) {
		this.parameter = parameter;
	}
	public String getXPlaneName() {
		return XPlaneName;
	}
	public void setXPlaneName(String xPlaneName) {
		XPlaneName = xPlaneName;
	}
}
