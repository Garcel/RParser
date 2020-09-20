package com.garcel.parser.r.node;

import com.garcel.parser.r.autogen.Node;

import static com.garcel.parser.r.autogen.RTreeConstants.*;

/**
 * RNodeFactory.java -
 *
 * @author : José Antonio Garcel
 */
public class RNodeFactory {

  public static Node jjtCreate(int id) {
    switch (id) {
      case JJTARGUMENTS: return new Arguments(id);
      case JJTASSIGNMENT: return new Assignment(id);
      case JJTBLOCK: return new Block(id);
      case JJTCONDITION: return new Condition(id);
      case JJTCONSTANT: return new Constant(id);
      case JJTEXPRESSION: return new Expression(id);
      case JJTEXPRESSIONLIST: return new ExpressionList(id);
      case JJTFOR: return new For(id);
      case JJTFORMLIST: return new FormList(id);
      case JJTFUNCTION: return new Function(id);
      case JJTHELP: return new Help(id);
      case JJTIDENTIFIER: return new Identifier(id);
      case JJTIF: return new If(id);
      case JJTPROGRAM: return new Program(id);
      case JJTREPEAT: return new Repeat(id);
      case JJTSUB: return new Sub(id);
      case JJTSUBLIST: return new SubList(id);
      case JJTWHILE: return new While(id);

      default: return new RNode(id);
    }
  }
}
