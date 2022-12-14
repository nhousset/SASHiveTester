[![Activity](https://img.shields.io/github/commit-activity/m/nhousset/SASHiveTester)](https://github.com/nhousset/SASHiveTester)
[![Stars](https://img.shields.io/github/stars/nhousset?style=social)](https://github.com/nhousset/SASHiveTester)
[![Website](https://img.shields.io/website?down_color=red&down_message=down&up_color=green&up_message=up&url=https://www.nicolas-housset.fr)](https://www.nicolas-housset.fr)
[![Twitter](https://img.shields.io/twitter/follow/nicolas_housset?style=social)](https://twitter.com/nicolas_housset)
[![Youtube](https://img.shields.io/youtube/channel/views/UCHxbJPkSGlJxtvPVrmzjxbg?style=social)](https://www.youtube.com/channel/UCHxbJPkSGlJxtvPVrmzjxbg)
# SASHiveTester

## Overview 
SASHiveTester is a tool to quickly test your connection to Hive, using beeline. 
This project includes the hadoop jar and the beeline client. It is not necessary to download them separately. 

## How to

After cloning the repository, the connection is done simply by running the beeline.sh shell in the scripts directory

```
cd scripts/
./beeline.sh
```

## Configuration

The tool uses the following environment variables: 

| Variable  | Default value | Description |
|-----------|-------------------|-------------|
| JAVA_HOME | /usr/lib/jvm/jre-1.8.0                  | By default, on the JRE used by SAS Viya           |
| HADOOP_HOME         | ../hadoop-dist/hadoop-2.7.3                 | hadoop distribution embedded in this project           |
| HIVE_HOME         | ../hive/1.2.1                 | Hive jar client embedded in this project           |

You can override the environment variables by exporting them before running the beeline.sh script :

```
export HADOOP_HOME=/opt/hadoop-dis/2.7.3
```

## Example of output
![sashivetester_output](https://github.com/nhousset/SASHiveTester/blob/main/src/sashivetester_output.jpeg?raw=true)

## Directory navigation

 - `hadoop-dist` contains the Hadoop distribution embedded in this project.
 - `hive` contains the Hive jar client embedded in this project.
 - `scripts` contains the scripts.
 - `src` contains pictures and other stuff.


If you are looking for a complete tool to audit your Viya 3.5 environment, you may be interested in my project  [Micro Service Health Check VIYA 3.5](https://github.com/nhousset/viyaTools/blob/main/msHealthCheck.md).
