source /home/hadoop/.bashrc

export basedir=$(cd `dirname $0`/..; pwd)

jar_file="${basedir}/jars/produce-1.0-SNAPSHOT.jar"

#echo $jar_file
export jar="${basedir}/libs/"

MAIN_CLASS=org.wanglei.zookeeper.MasterSelection

java -Djava.ext.dirs=${jar}   -cp ${jar_file}  ${MAIN_CLASS}
#java  ${MAIN_CLASS} 

result=$?
if [ $result -eq 0 ];then
    
   echo "Succeed"
   exit 0
else

   echo "Failed"
   exit 1
fi
