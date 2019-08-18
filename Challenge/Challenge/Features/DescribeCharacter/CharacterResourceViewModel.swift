import Marvel
import Foundation

struct CharacterResourceViewModel {
    let image: String
    let title: String
}

extension CharacterResourceViewModel {
    init(comics: Comics) {
        title = comics.title ?? ""
        image = ((comics.thumbnail?.path ?? "") + "." + (comics.thumbnail?.extension ?? ""))
    }

    init(series: Series) {
        title = series.title ?? ""
        image = ((series.thumbnail?.path ?? "") + "." + (series.thumbnail?.extension ?? ""))
    }
}
