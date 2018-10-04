import java.util.Map;

public abstract class MathFunction {
    static MathFunction getFromString(final String str, Map<String, Double> variables) {
        return new MathFunction() {
            @Override
            int calc(int _x) {
                variables.put("x", (double) _x);
                return (int) new Object() {
                    int pos = -1, ch;

                    synchronized void nextChar() {
                        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                    }

                    synchronized boolean eat(int charToEat) {
                        while (ch == ' ') nextChar();
                        if (ch == charToEat) {
                            nextChar();
                            return true;
                        }
                        return false;
                    }

                    synchronized double parse() {
                        nextChar();
                        Expression x = parseExpression();
                        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                        return x.eval();
                    }

                    // Grammar:
                    // expression = term | expression `+` term | expression `-` term
                    // term = factor | term `*` factor | term `/` factor
                    // factor = `+` factor | `-` factor | `(` expression `)`
                    //        | number | functionName factor | factor `^` factor

                    synchronized Expression parseExpression() {
                        Expression x = parseTerm();
                        for (; ; ) {
                            if (eat('+')) { // addition
                                Expression a = x, b = parseTerm();
                                x = (() -> a.eval() + b.eval());
                            } else if (eat('-')) { // subtraction
                                Expression a = x, b = parseTerm();
                                x = (() -> a.eval() - b.eval());
                            } else {
                                return x;
                            }
                        }
                    }

                    synchronized Expression parseTerm() {
                        Expression x = parseFactor();
                        for (; ; ) {
                            if (eat('*')) {
                                Expression a = x, b = parseFactor();
                                x = (() -> a.eval() * b.eval()); // multiplication
                            } else if (eat('/')) {
                                Expression a = x, b = parseFactor();
                                x = (() -> a.eval() / b.eval()); // division
                            } else {
                                return x;
                            }
                        }
                    }

                    synchronized Expression parseFactor() {
                        if (eat('+')) return parseFactor(); // unary plus
                        if (eat('-')) return (() -> -parseFactor().eval()); // unary minus

                        Expression x;
                        int startPos = this.pos;
                        if (eat('(')) { // parentheses
                            x = parseExpression();
                            eat(')');
                        } else if (ch >= 'a' && ch <= 'z') { // functions
                            while (ch >= 'a' && ch <= 'z') {
                                nextChar();
                            }
                            String func = str.substring(startPos, this.pos);
                            if (variables.containsKey(func)) x = (() -> variables.get(func));
                            else {
                                x = parseFactor();
                                if (func.equals("sqrt")) {
                                    Expression a = x;
                                    x = (() -> Math.sqrt(a.eval()));
                                } else if (func.equals("sin")) {
                                    Expression a = x;
                                    x = (() -> Math.sin(Math.toRadians(a.eval())));
                                } else if (func.equals("cos")) {
                                    Expression a = x;
                                    x = (() -> Math.cos(Math.toRadians(a.eval())));
                                } else if (func.equals("tan")) {
                                    Expression a = x;
                                    x = (() -> Math.tan(Math.toRadians(a.eval())));
                                } else throw new RuntimeException("Unknown function: " + func);
                            }
                        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                            while ((ch >= '0' && ch <= '9') || ch == '.') {
                                nextChar();
                            }
                            final int i = this.pos;
                            x = (() -> {
                                String s = str.substring(startPos, i);
                                return Double.parseDouble(s);
                            });
                        } else {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }
                        if (eat('^')) {
                            Expression a = x, b = parseFactor();
                            x = (() -> Math.pow(a.eval(), b.eval())); // exponentiation
                        }
                        return x;
                    }

                }.parse();
            }
        };
    }

    abstract int calc(int x);

    @FunctionalInterface
    interface Expression {
        double eval();
    }
}
