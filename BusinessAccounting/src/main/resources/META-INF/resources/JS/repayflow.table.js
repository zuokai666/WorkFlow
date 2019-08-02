var $table3 = $('#table3');

function initTable3() {
    $table3.bootstrapTable({
    	url: "../loan/repayflowAction?loanId=0",
    	reinit: false,
    	classes: "table table-hover",
    	pagination: true,
    	striped: true,
    	responseHandler: function (res) {
            return res;
        },
        columns:
            [
            	{
                    field: 'id',
                    title: '编号',
                    visiable: 'false'
                }, {
                    field: 'repayMode',
                    title: '还款类型',
                    align: 'center',
                    formatter:function(value,row,index){
                    	if(value == "dqhk"){
                    		return "到期还款";
                    	}else if(value == "tqjqCur"){
                    		return "提前结清当期";
                    	}else if(value == "tqjqAll"){
                    		return "提前结清全部";
                    	}else {
                    		return value;
                    	}
                    }
                }, {
                    field: 'repayDate',
                    title: '支付日',
                    align: 'center'
                }, {
                    field: 'paidAmount',
                    title: '实还金额',
                    align: 'center'
                }, {
                    field: 'paidPrincipal',
                    title: '实还本金',
                    align: 'center'
                }, {
                    field: 'paidInterest',
                    title: '实还利息',
                    align: 'center'
                }
            ]
    });
}