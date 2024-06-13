import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFormationInitiale } from '../formation-initiale.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../formation-initiale.test-samples';

import { FormationInitialeService, RestFormationInitiale } from './formation-initiale.service';

const requireRestSample: RestFormationInitiale = {
  ...sampleWithRequiredData,
  dateOuverture: sampleWithRequiredData.dateOuverture?.format(DATE_FORMAT),
  dateCloture: sampleWithRequiredData.dateCloture?.format(DATE_FORMAT),
  dateConcours: sampleWithRequiredData.dateConcours?.format(DATE_FORMAT),
};

describe('FormationInitiale Service', () => {
  let service: FormationInitialeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFormationInitiale | IFormationInitiale[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormationInitialeService);
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

    it('should create a FormationInitiale', () => {
      const formationInitiale = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(formationInitiale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormationInitiale', () => {
      const formationInitiale = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(formationInitiale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FormationInitiale', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FormationInitiale', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FormationInitiale', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFormationInitialeToCollectionIfMissing', () => {
      it('should add a FormationInitiale to an empty array', () => {
        const formationInitiale: IFormationInitiale = sampleWithRequiredData;
        expectedResult = service.addFormationInitialeToCollectionIfMissing([], formationInitiale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formationInitiale);
      });

      it('should not add a FormationInitiale to an array that contains it', () => {
        const formationInitiale: IFormationInitiale = sampleWithRequiredData;
        const formationInitialeCollection: IFormationInitiale[] = [
          {
            ...formationInitiale,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFormationInitialeToCollectionIfMissing(formationInitialeCollection, formationInitiale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormationInitiale to an array that doesn't contain it", () => {
        const formationInitiale: IFormationInitiale = sampleWithRequiredData;
        const formationInitialeCollection: IFormationInitiale[] = [sampleWithPartialData];
        expectedResult = service.addFormationInitialeToCollectionIfMissing(formationInitialeCollection, formationInitiale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formationInitiale);
      });

      it('should add only unique FormationInitiale to an array', () => {
        const formationInitialeArray: IFormationInitiale[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const formationInitialeCollection: IFormationInitiale[] = [sampleWithRequiredData];
        expectedResult = service.addFormationInitialeToCollectionIfMissing(formationInitialeCollection, ...formationInitialeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formationInitiale: IFormationInitiale = sampleWithRequiredData;
        const formationInitiale2: IFormationInitiale = sampleWithPartialData;
        expectedResult = service.addFormationInitialeToCollectionIfMissing([], formationInitiale, formationInitiale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formationInitiale);
        expect(expectedResult).toContain(formationInitiale2);
      });

      it('should accept null and undefined values', () => {
        const formationInitiale: IFormationInitiale = sampleWithRequiredData;
        expectedResult = service.addFormationInitialeToCollectionIfMissing([], null, formationInitiale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formationInitiale);
      });

      it('should return initial array if no FormationInitiale is added', () => {
        const formationInitialeCollection: IFormationInitiale[] = [sampleWithRequiredData];
        expectedResult = service.addFormationInitialeToCollectionIfMissing(formationInitialeCollection, undefined, null);
        expect(expectedResult).toEqual(formationInitialeCollection);
      });
    });

    describe('compareFormationInitiale', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFormationInitiale(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFormationInitiale(entity1, entity2);
        const compareResult2 = service.compareFormationInitiale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFormationInitiale(entity1, entity2);
        const compareResult2 = service.compareFormationInitiale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFormationInitiale(entity1, entity2);
        const compareResult2 = service.compareFormationInitiale(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
