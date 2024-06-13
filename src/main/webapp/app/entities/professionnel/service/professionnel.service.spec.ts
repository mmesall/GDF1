import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IProfessionnel } from '../professionnel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../professionnel.test-samples';

import { ProfessionnelService, RestProfessionnel } from './professionnel.service';

const requireRestSample: RestProfessionnel = {
  ...sampleWithRequiredData,
  dateNaiss: sampleWithRequiredData.dateNaiss?.format(DATE_FORMAT),
};

describe('Professionnel Service', () => {
  let service: ProfessionnelService;
  let httpMock: HttpTestingController;
  let expectedResult: IProfessionnel | IProfessionnel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProfessionnelService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Professionnel', () => {
      const professionnel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(professionnel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Professionnel', () => {
      const professionnel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(professionnel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Professionnel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Professionnel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Professionnel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProfessionnelToCollectionIfMissing', () => {
      it('should add a Professionnel to an empty array', () => {
        const professionnel: IProfessionnel = sampleWithRequiredData;
        expectedResult = service.addProfessionnelToCollectionIfMissing([], professionnel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(professionnel);
      });

      it('should not add a Professionnel to an array that contains it', () => {
        const professionnel: IProfessionnel = sampleWithRequiredData;
        const professionnelCollection: IProfessionnel[] = [
          {
            ...professionnel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, professionnel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Professionnel to an array that doesn't contain it", () => {
        const professionnel: IProfessionnel = sampleWithRequiredData;
        const professionnelCollection: IProfessionnel[] = [sampleWithPartialData];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, professionnel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(professionnel);
      });

      it('should add only unique Professionnel to an array', () => {
        const professionnelArray: IProfessionnel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const professionnelCollection: IProfessionnel[] = [sampleWithRequiredData];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, ...professionnelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const professionnel: IProfessionnel = sampleWithRequiredData;
        const professionnel2: IProfessionnel = sampleWithPartialData;
        expectedResult = service.addProfessionnelToCollectionIfMissing([], professionnel, professionnel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(professionnel);
        expect(expectedResult).toContain(professionnel2);
      });

      it('should accept null and undefined values', () => {
        const professionnel: IProfessionnel = sampleWithRequiredData;
        expectedResult = service.addProfessionnelToCollectionIfMissing([], null, professionnel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(professionnel);
      });

      it('should return initial array if no Professionnel is added', () => {
        const professionnelCollection: IProfessionnel[] = [sampleWithRequiredData];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, undefined, null);
        expect(expectedResult).toEqual(professionnelCollection);
      });
    });

    describe('compareProfessionnel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProfessionnel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProfessionnel(entity1, entity2);
        const compareResult2 = service.compareProfessionnel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProfessionnel(entity1, entity2);
        const compareResult2 = service.compareProfessionnel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProfessionnel(entity1, entity2);
        const compareResult2 = service.compareProfessionnel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
