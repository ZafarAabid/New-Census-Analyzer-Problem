package censusanalyser;

public class CvsLoaderFactory {

    public enum Country {
        INDIA {
            public CensusAdapter create() {
                return new IndiaCensusAdapter();
            }
        },
        US {
            public CensusAdapter create() {
                return new USCensusAdapter();
            }
        };

        public CensusAdapter create() {
            return null;
        }
    }

    public static CensusAdapter createAdaptor(Country validatorType) {
        return validatorType.create();
    }
}
