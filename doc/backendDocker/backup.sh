#!/bin/bash
USER=root
PASSWORD=admin@123
DATABASE=ipet
#备份数据库文件的路径
BACKUP_DIR=/home/sensetime/ipet/backup
#备份数据库脚本的日志文件
LOGFILE=/home/sensetime/ipet/backup/lastipet_backup.log
#获取当前系统时间-3分钟
DATE=`date +%Y%m%d-%H%M -d -3minute`
#需要备份的数据库名称
DUMPFILE=$DATE-ipet.sql
#判断备份路径是否存在，若不存在则创建该路径
if [ ! -d $BACKUP_DIR ];
then
mkdir -p "$BACKUP_DIR"
fi
#累加记录备份时间
echo -e "\n" >> $LOGFILE
echo "------------------------------------" >> $LOGFILE
echo "BACKUP DATE:$DATE">> $LOGFILE
echo "------------------------------------" >> $LOGFILE

#跳到备份路径下
cd $BACKUP_DIR
#使用mysqldump备份数据库 
/usr/bin/mysqldump -u$USER -p$PASSWORD $DATABASE > $DUMPFILE

#定义需要删除的文件距离当前的天数
KEEPTIME=30
#找到天数大于30天的文件
find $BACKUP_DIR -type f -mtime +$KEEPTIME | xargs rm -rf
