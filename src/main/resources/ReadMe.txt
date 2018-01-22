Hbase存储数据需要新建表
create 'tvcount', {NAME=>'tvinfo' ,VERSIONS=>'30'}

=》
           更新： 使用url作为仓库。各个节点共享。
           
=》
          更新：使用多线程（防止ip被封。设置休眠时长）