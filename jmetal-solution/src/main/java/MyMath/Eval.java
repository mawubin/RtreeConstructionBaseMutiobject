package MyMath;

import java.lang.reflect.Method;
import java.util.Vector;

public class Eval implements IEval {
	private boolean shouldContinue = true;

	private Vector stackOpnd = null;

	private Vector stackOptr = null;

	private Vector checkStr = null;

	private int numLength = 0;

	public Eval() {
	}

	private String myTrimAndCheck(String str) throws Exception {
		String temp = "";
		shouldContinue = true;
		for (int i = 0; i < str.length(); i++) {
			char xChar = str.charAt(i);
			if (xChar >= '0' && xChar <= '9' || xChar == '(' || xChar == ')'
					|| xChar == '+' || xChar == '-' || xChar == '*'
					|| xChar == '/' || xChar == '.') {
				temp = temp + xChar;
			} else if (xChar != ' ') {
				temp = "";
				shouldContinue = false;
				throw new Exception("����ʽ��ʽ���󣡣���Ч���š�" + xChar + "����");
			}
		}
		return temp;
	}

	private String getNumber(String str) throws Exception {
		String tempNumber = "";
		numLength = 0;
		int count = 0;
		shouldContinue = true;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'
					|| str.charAt(i) == '.') {
				if (str.charAt(i) == '.') {
					count++;
				}
				if (count <= 1) {
					tempNumber = tempNumber + str.charAt(i);
				} else {
					shouldContinue = false;
					throw new Exception("����ʽ��ʽ���󣡣����ִ���");
				}
				numLength++;
			} else {
				break;
			}
		}
		return tempNumber;
	}

	/**
	 * ��������
	 * 
	 * @author ��������--����ҫ
	 * @param expression
	 *            ���ʽ�����ܰ�����ѧ������ֻ�������֡����Ż������������
	 * @return ���ʽ�����Ľ��
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public double eval(String expression) throws Exception {
		if (shouldContinue) {
			stackOpnd = new Vector();
			stackOptr = new Vector();
			stackOptr.addElement('#');
			checkStr = new Vector();
			String tempStr = scientificExpressionToNormal("(" + expression
					+ ")").replace("-(", "-1*(");
			tempStr = myTrimAndCheck(tempStr);
			while (tempStr.length() > 0) {
				if (tempStr.charAt(0) == '('
						&& (tempStr.charAt(1) != '+' && tempStr.charAt(1) != '-')
						|| tempStr.charAt(0) == ')' || tempStr.charAt(0) == '+'
						|| tempStr.charAt(0) == '-' || tempStr.charAt(0) == '*'
						|| tempStr.charAt(0) == '/') {
					checkStr.addElement(tempStr.charAt(0));
					tempStr = tempStr.substring(1, tempStr.length());
				} else if (tempStr.charAt(0) == '('
						&& (tempStr.charAt(1) == '+' || tempStr.charAt(1) == '-')) {
					checkStr.addElement(tempStr.charAt(0));
					tempStr = tempStr.substring(1, tempStr.length());
					if (tempStr.charAt(0) == '+') {
						tempStr = tempStr.substring(1, tempStr.length());
						checkStr.addElement(getNumber(tempStr));
						tempStr = tempStr
								.substring(numLength, tempStr.length());
					} else if (tempStr.charAt(0) == '-') {
						String tempStrNum = "-";
						tempStr = tempStr.substring(1, tempStr.length());
						tempStrNum = tempStrNum + getNumber(tempStr);
						double tempDouble = Double.parseDouble(tempStrNum);
						checkStr.addElement(tempDouble);
						tempStr = tempStr
								.substring(numLength, tempStr.length());
					}
				} else if (tempStr.charAt(0) >= '0' && tempStr.charAt(0) <= '9') {
					checkStr.addElement(getNumber(tempStr));
					tempStr = tempStr.substring(numLength, tempStr.length());
				}
			}
			try {
				while (checkStr.size() > 0) {
					if (checkStr.get(0).toString().charAt(0) >= '0'
							&& checkStr.get(0).toString().charAt(0) <= '9'
							|| checkStr.get(0).toString().charAt(0) == '-'
							&& checkStr.get(0).toString().length() > 1) {
						stackOpnd.add(0, checkStr.get(0));
						checkStr.removeElementAt(0);
					} else {
						switch (compareOptr(stackOptr.get(0).toString().charAt(
								0), checkStr.get(0).toString().charAt(0))) {
						case '<':
							stackOptr.add(0, checkStr.get(0));
							checkStr.removeElementAt(0);
							break;
						case '=':
							stackOptr.removeElementAt(0);
							checkStr.removeElementAt(0);
							break;
						case '>':
							char theta = stackOptr.remove(0).toString().charAt(
									0);
							double b = Double.parseDouble(stackOpnd.remove(0)
									.toString());
							double a = Double.parseDouble(stackOpnd.remove(0)
									.toString());
							stackOpnd.add(0, operate(a, theta, b));
							break;
						}
					}
				}
				shouldContinue = true;
				return Double.parseDouble(stackOpnd.remove(0).toString());
			} catch (Exception ex) {
				throw new Exception("����ʽ��ʽ���󣡣��߼�����");
			}
		} else {
			shouldContinue = true;
			return 0.0;
		}
	}

	private double operate(double a, char x, double b) {
		switch (x) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			return a / b;
		}
		return 0.0;
	}

	private char compareOptr(char first, char second) {
		switch (first) {
		case '+':
			switch (second) {
			case '+':
				return '>';
			case '-':
				return '>';
			case '*':
				return '<';
			case '/':
				return '<';
			case '(':
				return '<';
			case ')':
				return '>';
			}
			;
			break;
		case '-':
			switch (second) {
			case '+':
				return '>';
			case '-':
				return '>';
			case '*':
				return '<';
			case '/':
				return '<';
			case '(':
				return '<';
			case ')':
				return '>';
			}
			;
			break;
		case '*':
			switch (second) {
			case '+':
				return '>';
			case '-':
				return '>';
			case '*':
				return '>';
			case '/':
				return '>';
			case '(':
				return '<';
			case ')':
				return '>';
			}
			;
			break;
		case '/':
			switch (second) {
			case '+':
				return '>';
			case '-':
				return '>';
			case '*':
				return '>';
			case '/':
				return '>';
			case '(':
				return '<';
			case ')':
				return '>';
			}
			;
			break;
		case '(':
			switch (second) {
			case '+':
				return '<';
			case '-':
				return '<';
			case '*':
				return '<';
			case '/':
				return '<';
			case '(':
				return '<';
			case ')':
				return '=';
			}
			;
			break;
		case ')':
			switch (second) {
			case '+':
				return '>';
			case '-':
				return '>';
			case '*':
				return '>';
			case '/':
				return '>';
			case ')':
				return '>';
			}
			;
			break;
		case '#':
			return '<';
		}
		return 'x';
	}

	// ִ�д�һ����������ѧ����
	private String getPartResultWithOneParameter(String expression,
			String functionName) throws Exception {
		String result = null;
		// ������ѧ����
		Method[] m = Math.class.getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equals(functionName)
					&& m[i].getReturnType().getName().toLowerCase().equals(
							"double")) {
				result = m[i].invoke(null,
						new Object[] { Double.parseDouble(expression) })
						.toString();
				break;
			}
		}
		return "(" + result + ")";
	}

	// ִ�д�������������ѧ����
	private String getPartResultWithTwoParameter(String expression,
			String functionName) throws Exception {
		String result = null;
		String[] tempParameters = expression.replace(" ", "").split(",");
		double[] parameters = new double[2];
		parameters[0] = Double.parseDouble(tempParameters[0]);
		parameters[1] = Double.parseDouble(tempParameters[1]);
		// ������ѧ����
		Method[] m = Math.class.getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equals(functionName)
					&& m[i].getReturnType().getName().toLowerCase().equals(
							"double")) {
				result = m[i].invoke(null,
						new Object[] { parameters[0], parameters[1] })
						.toString();
				break;
			}
		}
		return "(" + result + ")";
	}

	// ��ֱ��ʽΪ�����֣���ĳ��ѧ����Ϊ�磬��Ϊ����ǰ��������������
	private String[] splitStr(String expression, String functionName)
			throws Exception {
		String[] result = new String[3];
		int tempIntB = expression.indexOf(functionName);
		int beginPosition = -1;
		int endPosition = -1;

		// ȷ�������ָ��Եķ�Χ
		if (tempIntB >= 0) {
			beginPosition = expression.indexOf("(", tempIntB);
			int counter = 0;
			for (int i = beginPosition; i < expression.length(); i++) {
				if (expression.charAt(i) == '(') {
					counter++;
				} else if (expression.charAt(i) == ')') {
					counter--;
				}
				if (counter == 0) {
					endPosition = i;
					break;
				}
			}
		}

		// ���ɡ�����ǰ����������������������
		result[0] = tempIntB == 0 ? "" : expression.substring(0, tempIntB);
		result[1] = expression.substring(beginPosition + 1, endPosition);
		result[2] = expression.substring(endPosition + 1);

		return result;
	}

	private String getMathFunctionName(String expression) {
		// һ�������ĺ���
		if (expression.indexOf("abs") >= 0) {
			return "abs";
		}
		if (expression.indexOf("acos") >= 0) {
			return "acos";
		}
		if (expression.indexOf("asin") >= 0) {
			return "asin";
		}
		if (expression.indexOf("atan") >= 0) {
			return "atan";
		}
		if (expression.indexOf("cbrt") >= 0) {
			return "cbrt";
		}
		if (expression.indexOf("ceil") >= 0) {
			return "ceil";
		}
		if (expression.indexOf("cos") >= 0) {
			return "cos";
		}
		if (expression.indexOf("cosh") >= 0) {
			return "cosh";
		}
		if (expression.indexOf("exp") >= 0) {
			return "exp";
		}
		if (expression.indexOf("expm1") >= 0) {
			return "expm1";
		}
		if (expression.indexOf("floor") >= 0) {
			return "floor";
		}
		if (expression.indexOf("log") >= 0 || expression.indexOf("ln") >= 0) {
			return "log";
		}
		if (expression.indexOf("log10") >= 0) {
			return "log10";
		}
		if (expression.indexOf("log1p") >= 0) {
			return "log1p";
		}
		if (expression.indexOf("rint") >= 0) {
			return "rint";
		}
		if (expression.indexOf("round") >= 0) {
			return "round";
		}
		if (expression.indexOf("signum") >= 0) {
			return "signum";
		}
		if (expression.indexOf("sin") >= 0) {
			return "sin";
		}
		if (expression.indexOf("sinh") >= 0) {
			return "sinh";
		}
		if (expression.indexOf("sqrt") >= 0) {
			return "sqrt";
		}
		if (expression.indexOf("tan") >= 0) {
			return "tan";
		}
		if (expression.indexOf("tanh") >= 0) {
			return "tanh";
		}
		if (expression.indexOf("todegrees") >= 0) {
			return "todegrees";
		}
		if (expression.indexOf("toradians") >= 0) {
			return "toradians";
		}
		if (expression.indexOf("ulp") >= 0) {
			return "ulp";
		}

		// ���������ĺ���
		if (expression.indexOf("atan2") >= 0) {
			return "atan2";
		}
		if (expression.indexOf("hypot") >= 0) {
			return "hypot";
		}
		if (expression.indexOf("ieeeremainder") >= 0) {
			return "ieeeremainder";
		}
		if (expression.indexOf("max") >= 0) {
			return "max";
		}
		if (expression.indexOf("min") >= 0) {
			return "min";
		}
		if (expression.indexOf("pow") >= 0) {
			return "pow";
		}
		return null;
	}

	/**
	 * ������ʽ
	 * 
	 * @author ��������--����ҫ
	 * @param expression
	 *            ���ʽ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @return ���ʽ�����Ľ��
	 * @throws Exception
	 */
	public double eval2(String expression) throws Exception {
		expression = "(" + expression.toLowerCase().replace(" ", "") + ")";
		String currentExpression = expression;
		String functionName = null;
		String[] temp = null;
		while (getMathFunctionName(currentExpression) != null) {// ���������ѧ����
			functionName = getMathFunctionName(currentExpression);
			temp = splitStr(currentExpression, functionName);
			if (getMathFunctionName(temp[1]) == null) {// ��������в�������ѧ����
				if (temp[1].indexOf(",") < 0) {// һ������
					currentExpression = temp[0]
							+ getPartResultWithOneParameter("" + eval(temp[1]),
									functionName) + temp[2];
				} else {// ��������
					String[] temp2 = temp[1].split(",");
					currentExpression = temp[0]
							+ getPartResultWithTwoParameter(eval(temp2[0])
									+ "," + eval(temp2[1]), functionName)
							+ temp[2];
				}
			} else {// ��������а�����ѧ����
				temp[1] = functionName + "(" + eval2(temp[1]) + ")";
				currentExpression = temp[0] + "(" + eval2(temp[1]) + ")"
						+ temp[2];
			}
		}
		return eval(currentExpression);
	}

	/**
	 * �� 
	 * <br/> 
	 * ��������--���㹫ʽ 
	 * <br/> 
	 * ��ʽ����ϸ֤������ 
	 * <br/> 
	 * ����ֵ������ �����ѧ������--���ء�Ϳ��ԣ 1998��1�µ�1�� ��97ҳ 
	 * <br/> 
	 * <br/> 
	 * ע�⣺�÷����������ڲ��ɵ��㣬�򲻿ɵ�����
	 * 
	 * @author ��������--����ҫ
	 * @param expression
	 *            ���ʽ��������Сд x ��ʾ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @param value
	 *            �ںδ��󵼡�
	 * @return ��ǰ���ʽ��ĳ��ĵ���
	 * @throws Exception
	 */
	public double differentiate(String expression, String value)
			throws Exception {
		double result = 0.0;
		expression = "0+" + expression.toLowerCase();
		expression = expression.replace("expm1", "ewpm1").replace("exp", "ewp")
				.replace("max", "maw");
		String firstExpression = expression.replace("x", "(" + value + ")")
				.replace("ewpm1", "expm1").replace("ewp", "exp").replace("maw",
						"max");
		String secondExpression = expression.replace("x",
				"(" + value + "+0.00000001)").replace("ewpm1", "expm1")
				.replace("ewp", "exp").replace("maw", "max");
		String thirdExpression = expression.replace("x",
				"(" + value + "+0.00000002)").replace("ewpm1", "expm1")
				.replace("ewp", "exp").replace("maw", "max");
		result = (-3 * eval2(firstExpression) + 4 * eval2(secondExpression) - eval2(thirdExpression)) * 1e8 / 2;
		return result;
	}

	/**
	 * ���� 
	 * <br/> 
	 * ��������--���������ι�ʽ 
	 * <br/> 
	 * ��ʽ����ϸ֤������ 
	 * <br/> 
	 * ����ֵ������ �����ѧ������--���ء�Ϳ��ԣ 1998��1�µ�1�� ��132ҳ
	 * 
	 * @author ��������--����ҫ
	 * @param expression
	 *            ���ʽ��������Сд x ��ʾ�����԰�����ѧ�������� java.util.Math ��ĺ���Ϊ׼��
	 * @param beginValue
	 *            ��ʼֵ���������ֵĿ�ʼֵ
	 * @param endValue
	 *            ����ֵ�������ֵĽ���ֵ
	 * 
	 * @param partNumber
	 *            �ֿ��������ֿ�Խ�࣬����Խ�ߣ���������ʱ�����
	 * @return ��ǰ���ʽ��ĳ����Χ�Ķ����֡����ַ�Χ�� beginValue �� endValue ȷ��
	 * @throws Exception
	 */
	public double integral(String expression, String beginValue,
			String endValue, int partNumber) throws Exception {
		double result = 0.0;
		double h = (Double.parseDouble(endValue) - Double
				.parseDouble(beginValue))
				/ partNumber;

		expression = expression.toLowerCase();
		expression = expression.replace("expm1", "ewpm1").replace("exp", "ewp")
				.replace("max", "maw");
		partNumber = partNumber % 2 == 1 ? (partNumber + 1) : partNumber;
		int m = partNumber / 2;

		result += eval2(expression.replace("x", "(" + beginValue + ")")
				.replace("ewpm1", "expm1").replace("ewp", "exp").replace("maw",
						"max"))
				+ eval2(expression.replace("x", "(" + endValue + ")").replace(
						"ewpm1", "expm1").replace("ewp", "exp").replace("maw",
						"max"))
				+ 4
				* eval2(expression.replace("x",
						"(" + beginValue + "+" + h + ")").replace("ewpm1",
						"expm1").replace("ewp", "exp").replace("maw", "max"));
		for (int k = 1; k < m; k++) {
			result += 2
					* eval2(expression.replace("x",
							"(" + beginValue + "+" + 2 * k * h + ")").replace(
							"ewpm1", "expm1").replace("ewp", "exp").replace(
							"maw", "max"))
					+ 4
					* eval2(expression.replace("x",
							"(" + beginValue + "+" + (2 * k + 1) * h + ")")
							.replace("ewpm1", "expm1").replace("ewp", "exp")
							.replace("maw", "max"));
		}
		result *= (h / 3);
		return result;
	}

	/**
	 * ����ѧ����ת��Ϊ��ͨ����
	 * 
	 * @author ��������--����ҫ
	 * @param scientificNumber
	 *            ��ѧ����
	 * @return ��ͨ����
	 * @throws Exception
	 */
	public String scientificExpressionToNormal(String scientificNumber) {
		String result = scientificNumber.toLowerCase().replace(" ", "");
		int currentLocationOfE = -1;
		int beginLocation = -1;
		int endLocation = -1;
		String[] temp = new String[2];

		// ��
		while ((currentLocationOfE = result.indexOf("e-")) >= 0) {
			int i = currentLocationOfE - 1;
			char currentChar = result.charAt(i);
			temp[0] = "";
			while (currentChar >= '0' && currentChar <= '9'
					|| currentChar == '.') {
				temp[0] = currentChar + temp[0];
				i--;
				if (i < 0) {
					beginLocation = i + 1;
					break;
				}
				currentChar = result.charAt(i);
				if (!(currentChar >= '0' && currentChar <= '9' || currentChar == '.')) {
					beginLocation = i + 1;
					break;
				}
			}

			i = currentLocationOfE + 2;
			currentChar = result.charAt(i);
			temp[1] = "";
			while (currentChar >= '0' && currentChar <= '9') {
				temp[1] = temp[1] + currentChar;
				i++;
				if (i >= result.length()) {
					endLocation = i - 1;
					break;
				}
				currentChar = result.charAt(i);
				if (!(currentChar >= '0' && currentChar <= '9')) {
					endLocation = i - 1;
					break;
				}
			}
			result = result.substring(0, beginLocation)
					+ scientificNumberToNormal(temp[0] + "e-" + temp[1])
					+ result.substring(endLocation + 1);
		}

		// ��
		while ((currentLocationOfE = result.indexOf("e")) >= 0) {
			int i = currentLocationOfE - 1;
			char currentChar = result.charAt(i);
			temp[0] = "";
			while (currentChar >= '0' && currentChar <= '9'
					|| currentChar == '.') {
				temp[0] = currentChar + temp[0];
				i--;
				if (i < 0) {
					beginLocation = i + 1;
					break;
				}
				currentChar = result.charAt(i);
				if (!(currentChar >= '0' && currentChar <= '9' || currentChar == '.')) {
					beginLocation = i + 1;
					break;
				}
			}

			i = currentLocationOfE + 1;
			currentChar = result.charAt(i);
			temp[1] = "";
			while (currentChar >= '0' && currentChar <= '9') {
				temp[1] = temp[1] + currentChar;
				i++;
				if (i >= result.length()) {
					endLocation = i - 1;
					break;
				}
				currentChar = result.charAt(i);
				if (!(currentChar >= '0' && currentChar <= '9')) {
					endLocation = i - 1;
					break;
				}
			}
			result = result.substring(0, beginLocation)
					+ scientificNumberToNormal(temp[0] + "e" + temp[1])
					+ result.substring(endLocation + 1);
		}

		return result;
	}

	private String scientificNumberToNormal(String scientificNumber) {
		String result = scientificNumber;
		String temp[] = result.split("e");
		String[] temp2 = new String[2];
		String sign = temp[0].startsWith("-") ? "-" : "";
		int secondPart = Integer.parseInt(temp[1]);
		while (secondPart > 0) {
			int dot = temp[0].indexOf(".");
			temp2[0] = temp[0].substring(0, dot);
			temp2[1] = temp[0].substring(dot + 1);
			temp[0] = temp2[0]
					+ (temp2[1].length() > 0 && !temp2[1].equals("0") ? (temp2[1]
							.charAt(0)
							+ "." + temp2[1].substring(1))
							: "0.0");
			secondPart--;
		}
		while (secondPart < 0) {
			int dot = temp[0].indexOf(".");
			temp2[0] = temp[0].substring(0, dot);
			temp2[1] = temp[0].substring(dot + 1);
			temp[0] = "0." + temp2[0] + temp2[1];
			secondPart++;
		}
		result = sign + temp[0];
		return result;
	}

//	public static void main(String[] args) {
//		try {
//			Eval eval = new Eval();
//			System.out.println(eval.eval2("-0.105*(tan(123)+sin(cos(-3*4)))"));
//			System.out.println("---------------------------------------------");
//			System.out.println(eval
//					.eval2("-0.105*abs(tan(123)+sin(cos(-3*4)))"));
//
//			System.out.println("������"
//					+ eval.differentiate("pow(x,2)+pow(x,3)", "2"));
//			System.out.println(eval.eval2("2*2+3*pow(2,2)"));
//			System.out.println(eval.integral("pow(x,2)+sin(x)", "0", "1", 100));
//			System.out.println(eval
//					.scientificExpressionToNormal("-1.23e-1+4.21312e3"));
//			System.out.println(eval.eval2("pow(1,3)/3-cos(1)")
//					- eval.eval2("pow(0,3)/3-cos(0)"));
//			System.out.println(eval.integral("tan(pow(x,x))", "0", "1", 5000));
//			System.out.println("��������" + eval.eval2("tan(pow(1,2))"));
//			System.out.println(eval.eval2("sin(-1*acos(0))"));
//
//			System.out.println(eval.integral("tan(pow(x,2))", "-3", "3", 3000));
//			System.out.println(eval.differentiate("tan(pow(x,2))", "3.14"));
//			System.out.println(eval.eval2("tan(pow(1,2))"));
//			System.out.println(eval.eval2("tan(pow(-1,2))"));
//			System.out.println(eval.eval2("4*tan(pow(0.75,2))"));
//			System.out.println(eval.eval2("4*tan(pow(0.5,2))"));
//			System.out.println(eval.eval2("4*tan(pow(0.25,2))"));
//			System.out.println(eval.eval2("2*tan(pow(0,2))"));
//
//			System.out.println("--------------------------");
//			System.out
//					.println(eval
//							.eval("(1.5574077246549023+1.5574077246549023+2.521750695343539+1.021367684884145+0.2503260302651001+0)/9"));
//			System.out
//					.println(eval
//							.eval2("tan(pow(1,2))+tan(pow(-1,2))+4*tan(pow(0.75,2))+4*tan(pow(0.5,2))+4*tan(pow(0.25,2))+2*tan(pow(0,2))"));
//
//			System.out.println(eval.eval2("tan(pow(3,2))"));
//			System.out.println(eval.eval2("tan(pow(-3,2))"));
//			System.out.println(eval.eval2("4*tan(pow(2.25,2))"));
//			System.out.println(eval.eval2("4*tan(pow(1.5,2))"));
//			System.out.println(eval.eval2("4*tan(pow(0.75,2))"));
//			System.out.println(eval.eval2("2*tan(pow(0,2))"));
//			System.out
//					.println(eval
//							.eval2("(tan(pow(3,2))+tan(pow(-3,2))+4*tan(pow(2.9,2))+4*tan(pow(2.8,2))+4*tan(pow(2.7,2))+4*tan(pow(2.6,2))+4*tan(pow(2.5,2))+4*tan(pow(2.4,2))+4*tan(pow(2.3,2))+4*tan(pow(2.2,2))+4*tan(pow(2.1,2))+4*tan(pow(2.0,2))+4*tan(pow(1.9,2))+4*tan(pow(1.8,2))+4*tan(pow(1.7,2))+4*tan(pow(1.6,2))+4*tan(pow(1.5,2))+4*tan(pow(1.4,2))+4*tan(pow(1.3,2))+4*tan(pow(1.2,2))+4*tan(pow(1.1,2))+4*tan(pow(1.0,2))+4*tan(pow(0.9,2))+4*tan(pow(0.8,2))+4*tan(pow(0.7,2))+4*tan(pow(0.6,2))+4*tan(pow(0.5,2))+4*tan(pow(0.4,2))+4*tan(pow(0.3,2))+4*tan(pow(0.2,2))+4*tan(pow(0.1,2)))/60"));
//
//			System.out.println("--------------------------");
//			System.out
//					.println(eval
//							.eval("(1.5574077246549023+1.5574077246549023+2.521750695343539+1.021367684884145+0.2503260302651001+0)/9"));
//
//			System.out.println(eval.scientificExpressionToNormal("1.2345e10"));
//
//			System.out.println(eval
//					.scientificExpressionToNormal("-5.307179586686775E-6"));
//			System.out.println(eval.eval2("sin(sin(3.14159+3.14159))"));
//			System.out.println(eval.eval2("sin(-5.307179586686775E-6)"));
//
//			System.out.println(eval.eval2("sin(pow(2,2)-2*3.1415926)"));
//			System.out.println(eval.eval2("sin(4)"));
//			System.out.println(eval
//					.differentiate("max((sin(x)+x)*10/x,2)", "1"));
//			System.out.println(eval.eval("-(1)"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
