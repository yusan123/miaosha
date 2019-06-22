#!/usr/bin/env bash
#set -x
result=0

function hello(){
    echo "hello friends !"
}

function jia(){
   result=$(($1 + $2))
}

function jian(){
    result=$(($1 - $2))

}
function cheng(){
    result=$(($1 * $2))
}

function chu(){
    result=$(($1 / $2))
}

function useage(){
    echo "num1 num2 fuhao ex: 10 12 jia"
    echo "it will print calcute result"
}



function main(){
    hello

#    "$*"会将所有的参数从整体上看做一份数据，而不是把每个参数都看做一份数据。
#    "$@"仍然将每个参数都看作一份数据，彼此之间是独立的。

    echo '$*表示所有参数没有双引号和$@一样有的话讲所有参数看作一份数据 is: '$*
    echo '$@表示所有参数没有双引号和$*一样有的话将每个参数看作一份数据 is: '$@
    echo '$?表示上一条命令的执行结果为0则正常非0为异常 is: '$?
    echo '$#表示参数个数 is: '$#
    echo '$$表示当前脚本进程id is: '$$
    echo '$0表示当前文件目录和文件名 is: '$0
    echo '$1表示第一个参数 is: '$1
    echo '$2表示第二个参数 is: '$2
    echo '$3表示第三个参数 is: '$3

    if [[ ! $# -eq "3" ]];then
        useage
        exit 1
    fi

    case $3 in
        jia)
        jia $1 $2
        ;;
        jian)
        jian $1 $2
        ;;
        cheng)
        cheng $1 $2
        ;;
        chu)
        chu $1 $2
        ;;
    esac
    echo "最后的结果是:"$result
}




function list_params2(){
    echo '遍历参数时 $@ 无论带不带双引号，循环参数个数次'
    for i in "$@"
    do
        echo $i
    done
}

function list_params1(){
    echo '遍历参数时 $* 带了双引号，只循环一次'
    for i in "$*"
    do
        echo $i
    done
}
list_params2 $@
list_params1 $*


main $*