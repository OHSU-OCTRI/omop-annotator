export const person = Object.freeze({
  id: 12345,
  monthOfBirth: 1,
  yearOfBirth: 1990,
  age: 22,
  gender: 'Male',
  race: 'White',
  ethnicity: 'Non-Hispanic'
});

export const visits = [
  Object.freeze({
    id: 1,
    visitType: 'Emergency',
    visitStart: '2022-01-01',
    visitEnd: '2022-01-01',
    providerName: 'Dr. Nick',
    careSiteName: 'Springfield Hospital',
    visitStartIsoDate: '2022-01-01'
  }),
  Object.freeze({
    id: 2,
    visitType: 'Inpatient Visit',
    visitStart: '2022-02-02',
    visitEnd: '2022-02-03',
    providerName: 'Dr. Hibbert',
    careSiteName: 'Shelbyville General Hospital',
    visitStartIsoDate: '2022-02-02'
  })
];

export const notes = [
  Object.freeze({
    id: 1,
    date: '2022-01-01',
    type: 'EHR',
    title: 'Long Note',
    text: 'All work and no play makes Jack a dull boy. All work and no play makes Jack a dull boy. All work and no play makes Jack a dull boy. Spoon.'
  }),
  Object.freeze({
    id: 1,
    date: '2022-01-02',
    type: 'Other',
    title: 'Short Note',
    text: 'All work and no play makes Jack a dull boy.'
  })
];

export const judgmentDto = {
  id: 13,
  userId: 3,
  poolEntryId: 12,
  annotationLabelId: 7,
  annotationLabels: [
    {
      id: 7,
      version: 0,
      createdAt: '2022-03-23T18:12:38.000+00:00',
      updatedAt: '2022-03-23T18:12:38.000+00:00',
      annotationSchema: {
        id: 5,
        version: 0,
        createdAt: '2022-03-23T18:12:37.000+00:00',
        updatedAt: '2022-03-23T18:12:37.000+00:00',
        name: 'Relevance Judgment',
        label: 'Relevance Judgment'
      },
      displayOrder: 1,
      accentColor: '#069335',
      displayLabel: 'Relevant',
      outputLabel: '1',
      label: 'Relevant'
    },
    {
      id: 8,
      version: 0,
      createdAt: '2022-03-23T18:12:38.000+00:00',
      updatedAt: '2022-03-23T18:12:38.000+00:00',
      annotationSchema: {
        id: 5,
        version: 0,
        createdAt: '2022-03-23T18:12:37.000+00:00',
        updatedAt: '2022-03-23T18:12:37.000+00:00',
        name: 'Relevance Judgment',
        label: 'Relevance Judgment'
      },
      displayOrder: 2,
      accentColor: '#e2f019',
      displayLabel: 'Partially Relevant',
      outputLabel: '2',
      label: 'Partially Relevant'
    },
    {
      id: 9,
      version: 0,
      createdAt: '2022-03-23T18:12:38.000+00:00',
      updatedAt: '2022-03-23T18:12:38.000+00:00',
      annotationSchema: {
        id: 5,
        version: 0,
        createdAt: '2022-03-23T18:12:37.000+00:00',
        updatedAt: '2022-03-23T18:12:37.000+00:00',
        name: 'Relevance Judgment',
        label: 'Relevance Judgment'
      },
      displayOrder: 3,
      accentColor: '#f40101',
      displayLabel: 'Not Relevant',
      outputLabel: '3',
      label: 'Not Relevant'
    }
  ]
};

export const poolEntryJudgments = [
  {
    judgmentId: 13,
    poolEntryId: 12,
    sortOrder: 1,
    documentId: 115496,
    annotation: 'Relevant'
  },
  {
    judgmentId: null,
    poolEntryId: 40,
    sortOrder: 2,
    documentId: 192829,
    annotation: null
  },
  {
    judgmentId: null,
    poolEntryId: 41,
    sortOrder: 3,
    documentId: 136698,
    annotation: null
  }
];

export default {
  judgmentDto,
  person,
  poolEntryJudgments,
  visits
};
