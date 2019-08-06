alter table OriginalRepayPlan add index idx_loanId (loanId) using BTREE;
alter table RepayPlan add index idx_loanId (loanId) using BTREE;


