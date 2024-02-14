FROM ubuntu:latest
LABEL authors="SPURGE"

ENTRYPOINT ["top", "-b"]