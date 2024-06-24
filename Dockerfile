# omop_annotator
#
# docker build --rm -t octri.ohsu.edu/omop_annotator --pull .

FROM octri.ohsu.edu/jarrunner:17
EXPOSE 8080
RUN mkdir /search_indices && \
    chown svcoctrikube:octrikube /search_indices
COPY --chown=svcoctrikube:octrikube target/omop_annotator.jar /app.jar
USER svcoctrikube