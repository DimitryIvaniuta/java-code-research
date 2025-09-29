package com.code.research.service.unchecked;

import java.util.List;

record ImportReport<T>(List<T> customers, List<ImportResult<T>> failures) {
}
