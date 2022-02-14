# omop_annotator
#
# docker build -t octri.ohsu.edu/omop_annotator --rm=true --pull .

FROM octri.ohsu.edu/jarrunner:11
EXPOSE 8080
COPY --chown=svcoctrikube:octrikube target/omop_annotator.jar /app.jar
USER svcoctrikube