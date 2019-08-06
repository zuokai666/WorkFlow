--用于根据借据查询还款计划
alter table OriginalRepayPlan add index idx_loanId (loanId) using BTREE;
alter table RepayPlan add index idx_loanId (loanId) using BTREE;
--用于根据账户查询优惠券
alter table Coupon add index idx_accountId (accountId) using BTREE;




