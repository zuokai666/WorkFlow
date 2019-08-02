var $table1 = $('#table1');

function initTable1() {
    $table1.bootstrapTable({
    	url: "../account/listAction",
    	reinit: false,
    	classes: "table table-hover",
    	pagination: true,
    	search: true,
    	striped: true,
    	responseHandler: function (res) {
            return res;
        },
        columns:
            [	
            	{
            		checkbox: true,
            	}, {
                    field: 'id',
                    title: '编号',
                    visiable: 'false'
                }, {
                    field: 'name',
                    title: '期数',
                    align: '姓名'
                }, {
                    field: 'idNumber',
                    title: '身份证号',
                    align: 'center'
                }, {
                    field: 'bankCardNo',
                    title: '银行卡号',
                    align: 'center'
                }, {
                    field: 'bankPhone',
                    title: '银行卡预留手机号',
                    align: 'center'
                }, {
                    field: 'amount',
                    title: '银行卡余额',
                    align: 'center'
                }
            ]
    });
}

$(function() {
	initTable();
});