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
    provider: 'Dr. Nick',
    careSite: 'Springfield Hospital'
  }),
  Object.freeze({
    id: 2,
    visitType: 'Inpatient Visit',
    visitStart: '2022-02-02',
    visitEnd: '2022-02-03',
    provider: 'Dr. Hibbert',
    careSite: 'Shelbyville General Hospital'
  })
];

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
  person,
  poolEntryJudgments,
  visits
};
