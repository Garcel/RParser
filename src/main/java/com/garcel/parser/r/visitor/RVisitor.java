/* Generated By:JavaCC: Do not edit this line. RVisitor.java Version 6.1_2 */
package com.garcel.parser.r.visitor;

import com.garcel.parser.r.node.*;

public interface RVisitor
{
  public void visit(RNode node, Object data);
  public void visit(Program node, Object data);
  public void visit(Expression node, Object data);
  public void visit(Condition node, Object data);
  public void visit(ExpressionList node, Object data);
  public void visit(SubList node, Object data);
  public void visit(Sub node, Object data);
  public void visit(FormList node, Object data);
  public void visit(Arguments node, Object data);
  public void visit(Assignment node, Object data);
  public void visit(Block node, Object data);
  public void visit(Constant node, Object data);
  public void visit(For node, Object data);
  public void visit(Function node, Object data);
  public void visit(Help node, Object data);
  public void visit(Identifier node, Object data);
  public void visit(If node, Object data);
  public void visit(Repeat node, Object data);
  public void visit(While node, Object data);
}
/* JavaCC - OriginalChecksum=af3188911a118bdbd123b94f1573cbb6 (do not edit this line) */
