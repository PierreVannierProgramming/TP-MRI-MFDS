#!/bin/bash 
 
rmiregistry -J-D-Djava.security.manager -J-Djava.security.policy=chat.policy -J-Dsun.rmi.log.debug=true -J-Dsun.rmi.server.exceptionTrace=true -J-Dsun.rmi.loader.logLevel=verbose -J-Dsun.rmi.dgc.logLevel=verbose -J-Dsun.rmi.transport.logLevel=verbose -J-Dsun.rmi.transport.tcp.logLevel=verbose -J-Djava.rmi.server.UseCodebaseOnly=false 
