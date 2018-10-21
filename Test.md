

<program> -> <function><main>
<main> -> fn main()<stmnts>
<stmnts> -> <stmnt>;<stmnts> | <stmnt>
<stmnt> -> <condStmnt>| <LoopStmnt> | <DeclStmnt>| <AssignStmnt> |<function_call><condStmnt> -> <if_stmt> | <switch_stmnt>
<LoopStmnt> -> <For_stmnt> | <while_stmt>
<AssignStmnt> -> id = <arithmetic_exp>
<DeclStmnt> -> <Type> <VarList>;
<VarList> -> <VarList>,id | id;
<arithmetic_exp> -> <arithmetic_exp> + <var> | <var>
