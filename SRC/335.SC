;**
;**	Logics for room 335 -- Dale's face
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 20, 1989
;**


(script#				335)

(include	"face.sh")

(public
	rm335				0
)


;**		In this situation, the PrintL function serves to print what PATTI
;**	says; the PrintP function is Dale's response.  Sure, it LOOKS stupid...


;(define	vDaleFace					335)


(synonyms
	(man stripper dale )
)


(enum												;**	RoomScript state values
	intro
	goodbye
	outtaHere
)


(local
	sawWaterClue
	sawBraClue 
	sawPantyhoseClue 
)


(instance rm335 of Room
	(properties
		picture		335
	)

	(method (init)
		(super init)

		(self
			setRegions		regBigFace,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		154  78)
		(Notify regBigFace EyeEast		188  81)
		(Notify regBigFace EyelidWest	152  78)
		(Notify regBigFace EyelidEast	187  81)
		(Notify regBigFace Nose			170 106)
		(Notify regBigFace Mouth		168 114)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said '(go<out), date')
					(Said 'fuck')
					(Said 'take/anyword/home')
					(Said 'ask/anyword/date')
					(Said 'ask, get/date')
					(Said '(lets<make), make/love')
					(Said 'anyword/make/love')
					(Said 'go/bed, penthouse')
					(Said 'give/job<blow')
					(Said 'give/blow<job')
					(Said 'give/head')
					(Said 'give/head<job')
					(Said 'eat, blow, lick, suck/man, ball, nut, cock'))
				(PrintP "\"No way, Patti! 
					I know your reputation.					
					As soon as you show me a clean bill of health I'll consider
					your offer!\"")
				(React rFrown 4)
			)

			((Said '/bill, health')
				(Print "Who cares about Bill's health?")
			)

			((or	(Said 'show/prick/patti')
					(Said 'show/prick'))
				(PrintL "\"Hey, Dale.
					Word has it you're the warden of a considerable penal colony!
					How about a tour?\"")
				(PrintL "\"Oh my Gawd!!\"")
				(React rSmile 20)
			)

			((or	(Said 'help')
					(Said '/help')
					(Said '//larry')
					(Said '/larry'))
				(PrintL "\"Dale, I simply have to find Larry; Larry Laffer.
					Is there anyway you could help me?\"")
				(PrintP "\"Patti, I'm no pathfinder, but I know this:
					the answer doesn't lie with anyone here in this village!\"")
			)

			((or	(Said '/maze, forest, map, bamboo')
					(Said '//maze, forest, map, bamboo'))
				(PrintP "\"I've heard about that bamboo forest being like a maze.
					I do know this, when all else fails: read the manual!\"")
			)

			((Said '/bar, menu, drink, club, dance')
				(PrintP "\"I've been meaning to do something about the poor
					service here!\"")
			)

			((Said 'give')
				(if (InRoom iPanties 330)
					(PrintP "\"Honey, that little pair of panties you gave me 
						is all I need from you!\"")
				else
					(PrintP "\"Honey, I'd like to help you in your quest,
						but I just can't.\"")
				)
			)

			((Said 'talk')
				(cond
					((and (not sawPantyhoseClue)
							(not (ego has iPantyhose)))
						(= sawPantyhoseClue TRUE)
						(PrintP "\"I notice you don't wear hosiery, Patti.
							Your bare legs look wonderful!\"")
					)
					((and (not sawBraClue)
							(not (ego has iBra)))
						(= sawBraClue TRUE)
						(PrintP "\"I see you don't believe in wearing needless
							undergarments, Patti.
							I could tell from the moment I saw you sitting out
							there in the audience you were a girl after my
							own heart.\"")
					)
					((and (not sawWaterClue)
							(or	(not (ego has iBottle))
									(!= ((Inventory at iBottle) view) ivWaterBottle)))
						(= sawWaterClue TRUE)
						(PrintP "\"I know my motto:
							don't get caught without a drink in your hand!\"")
					)
					(else
						(PrintL "\"So, Dale, is there any way through that treacherous
							bamboo forest that lies to the north of the village?\"")
						(PrintP "\"Patti, all I can say is: when all else fails,
							read your manual!\"")
					)
				)
			)

			((Said 'make/joke')
				(PrintL "You attempt to make a lame joke.")
				(PrintL "(You succeed.)" LOW)
				(PrintP "\"Hey, Patti! This is what I think of that joke!\"")
				(React rSilly 22)
			)

			((Said 'love/you')
				(PrintL "\"Baby, I thinks I loves ya!\"")
				(PrintP "\"I'm sure you would!\"")
				(React rLove 44)
			)

			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'look/room')
					(Said 'quit, leave, exit, quit, walk, go'))
				(self changeState goodbye)
			)

			((Said 'kiss')
				(Print "That's ONE thing you're hoping for!")
			)

			((Said '/beard')
				(Print "All the sexiest men have beards.")
				(Print "(See box cover.)" LOW)
			)

			((Said '/ass')
				(Print "Tight butts drive you nuts!")
			)

			((Said '/hand')
				(Print "His hands are large and powerful
					but it's not his hands you're interested in!")
			)

			((Said '/clothes')
				(Print "He looks even sexier dressed!")
			)

			((Said '/body')
				(Print "He has the body of a dancer!")
			)

			((Said '/feet')
				(Print "Is your latent foot fetish cropping up again, Patti?")
			)

			((Said '/hair')
				(Print "You feel balding men are more attractive!")
			)

			((Said '/ear')
				(Print "Dale's ears are not what attracted you to him.")
			)

			((Said '/nose')
				(Print "He has a nice rhythm to his breathing.")
			)

			((Said '/mouth, teeth')
				(Print "Doesn't he have the cutest smile!")
			)

			((Said '/eye')
				(Print "You stare deeply into his eyes. You see no signs of life.")
			)

			((or	(Said 'fondle, grab, touch')
					(Said '/ball, prick, nut'))
				(PrintL "\"What do you think of this, Dale?\"")
				(PrintP "\"Oooh! So it's true! You are `The Passionate One!'\"")
				(React rLove 44)
			)

			((Said '/tongue')
				(Print "Just think of the tales that tongue could tell...")
			)

			((Said 'look>')
				(cond
					((Said '/leg')
						(PrintP "\"Oh, Patti!\" smiles Dale.
							\"Keep looking at me like that!\"")
						(React rSexy 22)
					)

					((Said '/bracelet')
						(Print "Dale is wearing an excellent knockoff of an
							expensive, imported gold watch.")
					)

					((Said '/ear')
						(Print "His delicate ears have heard a cacophony
							of shrieking women.")
					)

					((Said '/tit')
						(Print "\"My, what a manly chest he has!\"
							you think to yourself.")
						(PrintP "\"Oh, Patti!\" smiles Dale.
							\"Please keep looking at me there!\"")
						(React rOh 12)
					)

					((Said '[/room, man, Dale]')
						(Print "Before you sits Dale the Male Stripper
							and co-owner of \"Chip 'n' Dale's.\"
							You hope he may be able to help you find Larry.")
						(PrintP "\"Hello,\" he says with a smile.")
						(React rSmile 33)
					)
				)
			)
			((or	(Said '(out<go), dance, show, date/')
					(Said 'take/man/home')
					(Said 'ask/man/date')
					(Said 'go/room, place, comic, club, date, walk, bar, show, dance'))
				(PrintL "\"Dale, if I had met you a few days ago,
					I would have asked you out on a date.
					But now, I'm only interested in Larry; Larry Laffer.\"")
				(PrintP "\"I hope you find him, Patti.\"")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(goodbye
				(PrintL "\"It's been a pleasure talking with you, Dale\"")
				(PrintP "\"Hope you get your man, Patti!\"")
				(React rSmile 55)
				(= cycles 50)
			)

			(outtaHere
				(curRoom newRoom 330)
			)
		)
	)
)

