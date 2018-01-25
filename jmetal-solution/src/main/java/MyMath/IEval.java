package MyMath;

public interface IEval {

	/**
	 * ����ѧ����ת��Ϊ��ͨ����
	 * 
	 * @param number ��ѧ����
	 * @return ��ͨ����
	 * @throws Exception
	 */
	public String scientificExpressionToNormal(String number) throws Exception;

	/**
	 * ����
	 * <br/>
	 * ��������--���������ι�ʽ
	 * <br/>
	 * ��ʽ����ϸ֤������
	 * <br/>
	 * @param expression ���ʽ��������Сд x ��ʾ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @param beginValue ��ʼֵ���������ֵĿ�ʼֵ
	 * @param endValue ����ֵ�������ֵĽ���ֵ
	 * 
	 * @param partNumber �ֿ��������ֿ�Խ�࣬����Խ�ߣ���������ʱ�����
	 * @return ��ǰ���ʽ��ĳ����Χ�Ķ����֡����ַ�Χ�� beginValue �� endValue ȷ��
	 * @throws Exception
	 */
	public double integral(String expression, String beginValue,
			String endValue, int partNumber) throws Exception;

	/**
	 * ��
	 * <br/>
	 * ��������--���㹫ʽ
	 * <br/>
	 * ��ʽ����ϸ֤������
	 * <br/>
	 * <br/>
	 * <br/>
	 * ע�⣺�÷����������ڲ��ɵ��㣬�򲻿ɵ�����
	 * @param expression ���ʽ��������Сд x ��ʾ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @param value �ںδ��󵼡�
	 * @return ��ǰ���ʽ��ĳ��ĵ���
	 * @throws Exception
	 */
	public double differentiate(String expression, String value)
			throws Exception;

	/**
	 * ������ʽ
	 * @param expression ���ʽ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @return ���ʽ�����Ľ��
	 * @throws Exception
	 */
	public double eval2(String expression) throws Exception;

	/**
	 * ��������
	 * @param expression ���ʽ�����ܰ�����ѧ������ֻ�������֡����Ż������������
	 * @return ���ʽ�����Ľ��
	 * @throws Exception
	 */
	public double eval(String expression) throws Exception;
}
