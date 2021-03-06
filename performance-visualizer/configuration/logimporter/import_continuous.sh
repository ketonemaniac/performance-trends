#!/bin/bash

#sh --login -i

INPUT_DIR=d:/logs/durations/
#d:/logs/durations
OUTPUT_DIR=c:/trends-visualizer/loginput

ORIGINAL_FILE_EXTENSION='*.data'

function containsElement {
 inputArray=$1
 possibleElement=$2
 present=$3
 contains=false
 echo "containsElement called with $possibleElement"
 case "${inputArray[@]}" in  *"$possibleElement"*) contains=true ;; esac
 echo $contains
 eval $present="'$contains'"
}

function loopForever {
  declare -a allTailedFiles=()
  while true
  do
    echo "Checking for new files..."
    dataFilesFromDirectory=`find "$INPUT_DIR" -type f -name $ORIGINAL_FILE_EXTENSION`
    #dataFilesFromDirectory=`find "$INPUT_DIR" $ORIGINAL_FILE_EXTENSION`
    declare -a newFilesToTail=()
    for file in $dataFilesFromDirectory; do
      containsElement "$allTailedFiles" $file constains
      echo $constains
      if [ "$constains" != true ]; then
        echo "$file not yet tailed, will start tailing it now"
        newFilesToTail=( ${newFilesToTail[@]} $file )
      fi
    done

    echo "new: ${newFilesToTail[@]}"

    allTailedFiles=( "${allTailedFiles[@]}" "${newFilesToTail[@]}" )

    echo "all: ${allTailedFiles[@]}"

    sleep 8
    # access element by id
    #echo ${filesBeingTailed[0]}

  done
}

loopForever

#element1=d:/logs/durations/4.data
#declare -a testArray=()
#testArray=( ${testArray[@]} $element1 )
#testArray=( ${testArray[@]} 'rty' )
#containsElement $testArray $element1 constains


# examplefor getting file lst modified date
# date +%Y-%m-%d-%H%M%S -r test1.data
# 2014-12-22-115559
# tail file from beginning: "tail -c +1 -f" 
function old {
if [ $numberOfInputFiles -gt 0 ]; then
  echo "Found $numberOfInputFiles files matching $ORIGINAL_FILE_EXTENSION to compact! Now starting to compact/merge file(s)."
  rm -rf $INPUT_DIR/*.index
  outputFileName=merged-`date +%Y-%m-%d-%H%M%S`.log
  find $INPUT_DIR -type f -name $ORIGINAL_FILE_EXTENSION -exec grep -a '`' {} \; > $OUTPUT_DIR/$outputFileName

  echo -e "\nOutput file is ready and it starts like this:"
  head -n 1 $OUTPUT_DIR/$outputFileName
  echo -e "\nAnd it ends like this:"
  tail -n 1 $OUTPUT_DIR/$outputFileName

  lineCount=`cat $INPUT_DIR/$outputFileName | wc -l`
  fileSize=`du -h $INPUT_DIR/$outputFileName | cut -f1`
  echo  "The resulted file has $lineCount lines and a size of $fileSize"
  echo -e "\nMoving file to output folder: $OUTPUT_DIR/$outputFileName"

  #rm -rf $INPUT_DIR/$ORIGINAL_FILE_EXTENSION
  echo -e "\nDone."

else
  echo "No $ORIGINAL_FILE_EXTENSION files found to merge!"
fi
}