drop table if EXISTS WorkFlowModel;
create table WorkFlowModel(
	flowNo varchar(255) not null,
	phaseNo varchar(255) not null,
	version varchar(255) default null,
	phaseType varchar(255) default null,
	phaseName varchar(255) default null,
	phaseDescribe varchar(255) default null,
	phaseAttribute varchar(255) default null,
	preScript varchar(255) default null,
	initScript varchar(255) default null,
	choiceDescribe varchar(255) default null,
	choiceScript varchar(255) default null,
	actionDescribe varchar(255) default null,
	actionScript varchar(255) default null,
	postScript varchar(255) default null,
	attribute1 varchar(255) default null,
	attribute2 varchar(255) default null,
	attribute3 varchar(255) default null,
	attribute4 varchar(255) default null,
	attribute5 varchar(255) default null,
	attribute6 varchar(255) default null,
	attribute7 varchar(255) default null,
	attribute8 varchar(255) default null,
	attribute9 varchar(255) default null,
	attribute10 varchar(255) default null,
	standardTime1 varchar(255) default null,
	standardTime2 varchar(255) default null,
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (flowNo,phaseNo)
)engine=InnoDB default charset=utf8 comment='流程模型';




drop table if EXISTS WorkFlowObject;
create table WorkFlowObject(
	objectNo varchar(255) not null,
	objectType varchar(255) not null,
	applyType varchar(255) default null comment '申请类型',
	flowNo varchar(255) default null comment '当前流程号',
	flowName varchar(255) default null comment '当前流程名称',
	phaseNo varchar(255) default null comment '当前阶段号',
	phaseType varchar(255) default null comment '当前阶段类型',
	phaseName varchar(255) default null comment '当前阶段名称',
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (objectNo,objectType)
)engine=InnoDB default charset=utf8 comment='流程对象表';





drop table if EXISTS WorkFlowTask;
create table WorkFlowTask(
	serialNo varchar(255) not null,
	objectNo varchar(255) default null,
	objectType varchar(255) default null,
	relativeSerialNo varchar(255) default null comment '上一流水号',
	flowNo varchar(255) default null comment '流程编号',
	flowName varchar(255) default null comment '流程名称',
	phaseNo varchar(255) default null comment '阶段编号',
	phaseName varchar(255) default null comment '阶段名称',
	phaseType varchar(255) default null comment '阶段类型',
	applyType varchar(255) default null comment '申请类型',
	beginTime varchar(255) default null comment '开始时间',
	endTime varchar(255) default null comment '结束时间',
	phaseChoice varchar(255) default null comment '阶段意见',
	phasePinion varchar(255) default null comment '意见详情',
	phasePinion1 varchar(255) default null comment '意见详情1',
	phasePinion2 varchar(255) default null comment '意见详情2',
	processTaskNo varchar(255) default null comment '流程任务编号',
	flowState varchar(255) default null comment '流程状态',
	update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	primary key (serialNo)
)engine=InnoDB default charset=utf8 comment='流程任务';





