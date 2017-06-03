package google;

import google.ExpressionTreeEvaluation.ExpressionTreeNode.Operator;

public class ExpressionTreeEvaluation {
	public static class ExpressionTreeNode {
		static enum Operator {
			Add, Subtract, Multiply, Divide;

			public int performOperation(int operand1, int operand2) {
				switch (this) {
				case Add:
					return operand1 + operand2;
				case Subtract:
					return operand1 - operand2;
				case Multiply:
					return operand1 * operand2;
				default:
					return operand1 / operand2;
				}
			}

		}

		public Boolean isLeaf;
		public Operator valIfOperator;
		public Integer valIfInteger;

		ExpressionTreeNode left;
		ExpressionTreeNode right;

		public ExpressionTreeNode(boolean isLeaf, Operator valIfOperator, Integer valIfInteger) {
			this.isLeaf = isLeaf;
			if (isLeaf == true && valIfOperator == null && valIfInteger != null) {
				this.valIfInteger = valIfInteger;
			} else if (isLeaf == false && valIfOperator != null && valIfInteger == null) {
				this.valIfOperator = valIfOperator;
			} else {
				throw new IllegalArgumentException();
			}

			left = null;
			right = null;
		}

	}

	public static void main(String args[]) {
		ExpressionTreeNode n1 = new ExpressionTreeNode(false, Operator.Add, null);
		ExpressionTreeNode n2 = new ExpressionTreeNode(false, Operator.Divide, null);
		ExpressionTreeNode n3 = new ExpressionTreeNode(false, Operator.Add, null);
		ExpressionTreeNode n4 = new ExpressionTreeNode(true, null, 5);
		ExpressionTreeNode n5 = new ExpressionTreeNode(true, null, 5);
		ExpressionTreeNode n6 = new ExpressionTreeNode(true, null, 6);
		ExpressionTreeNode n7 = new ExpressionTreeNode(true, null, -1);

		n1.left = n2;
		n1.right = n3;
		n2.left = n4;
		n2.right = n5;
		n3.right = n7;
		n3.left = n6;

		
		ExpressionTreeNode en1 = new ExpressionTreeNode(false, Operator.Add, null);
		ExpressionTreeNode en2 = new ExpressionTreeNode(true, null, 3);
		ExpressionTreeNode en3 = new ExpressionTreeNode(true, null, 3);
		
		en1.left = en2;
		en1.right = en3;
		System.out.println(evaluateExpression(n1) == evaluateExpression(en1));
	}

	private static int evaluateExpression(ExpressionTreeNode node) {
		if (node.isLeaf) {
			return node.valIfInteger;
		} else {
			int left = evaluateExpression(node.left);
			int right = evaluateExpression(node.right);
			return node.valIfOperator.performOperation(left, right);
		}
	}
}
