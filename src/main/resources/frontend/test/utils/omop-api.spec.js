import OmopApi from '@/utils/omop-api';

import { mockFetchResponse } from '../helpers';

describe('OMOP API', () => {
  const expectedPersonPrefix = '/data/api/person/summary';
  const api = new OmopApi();

  it('defaults to a blank URL prefix', () => {
    expect(api.personPrefix).toEqual(expectedPersonPrefix);
  });

  it('adds the URL prefix to URLs', () => {
    const customPrefix = '/omop_annotator';
    const api = new OmopApi(customPrefix);
    expect(api.personPrefix).toEqual(customPrefix + expectedPersonPrefix);
  });

  it('makes fetch requests', () => {
    const okResponse = mockFetchResponse({});
    spyOn(window, 'fetch').and.resolveTo(okResponse);

    api.getPerson(42);
    expect(window.fetch).toHaveBeenCalledWith(`${expectedPersonPrefix}/42`, {
      credentials: 'same-origin'
    });
  });

  it('rejects when requests are not successful', async () => {
    const failedResponse = mockFetchResponse('', {
      status: 401,
      statusText: 'Unauthorized'
    });

    spyOn(window, 'fetch').and.resolveTo(failedResponse);

    try {
      await api.getPerson(42);
      expect(false).toBe(true).withContext('request should have thrown an error');
    } catch (e) {
      expect(e.message).toEqual('Unauthorized');
    }
  });

  it('extracts and returns JSON data from successful requests', async () => {
    const person = {
      id: 12345,
      monthOfBirth: 1,
      yearOfBirth: 1990,
      age: 22,
      gender: 'Male',
      race: 'White',
      ethnicity: 'Non-Hispanic'
    };

    const okResponse = mockFetchResponse(person);

    spyOn(window, 'fetch').and.resolveTo(okResponse);

    const response = await api.getPerson(12345);
    expect(response).toEqual(person);
  });
});
